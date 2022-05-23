package com.kedacom.device.core.notify.cu.loadGroup;

import cn.hutool.core.collection.CollectionUtil;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.*;
import lombok.extern.slf4j.Slf4j;

import java.text.Collator;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 设备缓存。一个监控平台一个缓存。
 *
 * @author ycw
 * @date 2021/11/24
 */
@Slf4j
public class CuDeviceCache {


    /**
     * 监控平台内置根分组的ID。
     * 通过接口不能获取到内置根分组，只能通过识别“内置未分组”的parentId。
     */
    private String rootGroupId;

    /**
     * 监控平台上报的根分组ID
     */
    private String pubRootGroupId;

    private Object deviceLock = new Object(); //设备集合同步锁
    /**
     * 分组
     * key:分组ID, value：分组信息
     */
    private Hashtable<String, PGroup> groups = new Hashtable<String, PGroup>(20);

    private ArrayList<PGroup> sortGroups = new ArrayList<PGroup>(20); //经过排序的分组列表

    /**
     * 设备状态
     * key:设备puid, value：PChannelStatus对象（合成通道录像状态）
     */
    private Hashtable<String, PChannelStatus> status_map = new Hashtable<String, PChannelStatus>();

    /**
     * 设备
     * key:设备ID, value：设备信息
     */
    private Hashtable<String, PDevice> devices = new Hashtable<String, PDevice>(100);

    /**
     * 设备
     * key:1.0平台对应puId, value：设备信息
     */
    private Hashtable<String, PDevice> devicesOne = new Hashtable<String, PDevice>(100);

    /**
     * 设备
     * key:分组ID, value：分组下的设备，根据名称排序。
     */
    private Hashtable<String, List<PDevice>> devicesByGroup = new Hashtable<String, List<PDevice>>(100);

    public void setRootGroupId(String rootGroupId) {
        this.rootGroupId = rootGroupId;
    }

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();


    /**
     * 设备是否加载完成
     */
    private boolean loadComplete = false;

    public CuDeviceCache() {

    }

    /**
     * 清空数据
     */
    public void clear() {
        this.groups.clear();
        this.sortGroups.clear();
        this.devices.clear();
        this.devicesOne.clear();
        this.devicesByGroup.clear();
        this.status_map.clear();
    }

    /**
     * 增加分组
     *
     * @param group
     */
    public void addDeviceGroup(PGroup group) {
        if (group.isRootGroup(group)) {
            /*
             * 查找根分组
             */
            this.rootGroupId = group.getId();
        }

        if (ToolsUtil.isEmpty(group.getParentId())) {
            this.pubRootGroupId = group.getId();
        }

        this.groups.put(group.getId(), group);
        checkSortRootGroups(group);

        //维护分组的树状结构
        //2018-10-16 LinChaoYu 连接平台1.0时，出现 上级分组ID 与 分组ID相同：0000000000000000000000000
        if (group.getParentId() == null || group.getParentId().equals(group.getId())) {
            addSortRootGroups(group);
        } else {
            PGroup parent = groups.get(group.getParentId());
            if (parent != null) {
                parent.addChildGroup(group);
                group.setParentId(parent.getId());
            } else {
                addSortRootGroups(group);
            }
        }

    }

    public void addDeviceGroups(Collection<PGroup> groups) {
        for (PGroup g : groups) {
            this.addDeviceGroup(g);
        }
    }

    private void checkSortRootGroups(PGroup group) {
        synchronized (sortGroups) {
            if (group == null)
                return;
            //寻找当前所有根分组的父分组是否为group分组，如果是，则加为group的子分组，并从根分组中删除。
            ListIterator<PGroup> it = sortGroups.listIterator();
            while (it.hasNext()) {
                PGroup temp = it.next();
                if (temp.getParentId() != null && temp.getParentId().equals(group.getId())) {
                    group.addChildGroup(temp);
                    temp.setParentId(group.getId());
                    it.remove();
                }
            }
        }
    }

    private void addSortRootGroups(PGroup group) {
        synchronized (sortGroups) {
            if (group == null)
                return;
            //排序加入缓存
            for (int index = sortGroups.size() - 1; index >= 0; index--) {
                PGroup temp = sortGroups.get(index);
                if (compareZH_CN(temp.getName(), group.getName()) < 0) {
                    sortGroups.add(index + 1, group);
                    return;
                }
            }
            sortGroups.add(0, group);
        }
    }

    /**
     * 根据分组ID获取分组信息
     *
     * @param groupId
     * @return
     */
    public PGroup getPGroupById(String groupId) {
        return groups.get(groupId);
    }

    /**
     * 增加设备
     *
     * @param device
     */
    public void addDevice(PDevice device) {
        log.info("==加载单个设备{}", device);
        //loadPuIdOne(device); //加载设备平台1.0对应PuId
        String puid = device.getPuId();
        //根据puid判断设备是否已经存在，如果存在就忽略掉，避免重复
        PDevice pDevice = this.devices.get(puid);
        if (pDevice != null) {
            PDevice pDbk = new PDevice();
            pDbk.setType(pDevice.getType());
            pDbk.setDomain(pDevice.getDomain());
            pDbk.setGroupId(pDevice.getGroupId());
            pDbk.setManufact(pDevice.getManufact());
            pDbk.setName(pDevice.getName());
            pDbk.setOnline(pDevice.getOnline());
            pDbk.setPrilevel(pDevice.getPrilevel());
            pDbk.setPuId(pDevice.getPuId());
            pDbk.setSrcChns(pDevice.getSrcChns());
            this.devices.put(puid, pDbk);
            //把平台1.0puId和对应设备存储在容器里
            //this.devicesOne.put(device.getPuIdOne(), pDbk);
        } else {
            this.devices.put(puid, device);
            //this.devicesOne.put(device.getPuIdOne(), device);
        }

        String groupId = device.getGroupId();
        if (ToolsUtil.isEmpty(groupId)) {// 2019-09-24 设备入网通知中，设备所属分组为空
            groupId = this.pubRootGroupId;
            device.setGroupId(groupId);
        } else if (this.rootGroupId != null && groupId.equalsIgnoreCase(this.rootGroupId)) {
            //根分组设备设置
            groupId = this.rootGroupId;
            device.setGroupId(groupId);
        }


        List<PDevice> list = this.devicesByGroup.get(groupId);
        if (list == null) {
            list = Collections.synchronizedList(new ArrayList<PDevice>());
            this.devicesByGroup.put(groupId, list);
        }

        int i = 0;
        for (; i < list.size(); i++) {
            PDevice dev = list.get(i);
            if (device.getName().compareTo(dev.getName()) <= 0) {
                break;
            }
        }
        list.add(i, device);

    }

    public void addDevices(Collection<PDevice> devices) {
        log.info("addDevices:{}", devices);
        for (PDevice device : devices) {
            this.addDevice(device);
        }
    }

    /**
     * 根据2.0平台puId获取设备
     *
     * @param puId
     * @return
     */
    public PDevice getDevice(String puId) {
        return this.devices.get(puId);
    }

    /**
     * 根据1.0平台puId获取设备
     *
     * @param puId
     * @return
     */
    public PDevice getDeviceByOne(String puId) {
        log.info("1.0puID和设备容器情况{}", devicesOne);
        return this.devicesOne.get(puId);
    }

    /**
     * 删除设备
     *
     * @param puid
     */
    public void removeDevice(String puid) {

        synchronized (deviceLock) {
            PDevice device = this.getDevice(puid);
            if (device != null) {
                String groupId = device.getGroupId();
                List<PDevice> list = this.devicesByGroup.get(groupId);
                if (list != null) {
                    list.remove(device);
                }
            }

            this.devices.remove(puid); //这一行要放在后面，否则调用this.getDevice(puid); 返回的会是null
        }

    }

    /**
     * 更新设备在线状态
     *
     * @param puid   设备ID
     * @param online 在线状态
     */
    public void updateDeviceStatus(String puid, Integer online) {
        writeLock.lock();
        try {
            for (List<PDevice> devices : devicesByGroup.values()) {
                for (PDevice pDevice : devices) {
                    if (puid.equals(pDevice.getPuId())) {
                        if (online != null) {
                            pDevice.setOnline(online);
                            if (online == 0) {
                                if (online == 0) {
                                    //设备下线，所有通道全部下线
                                    List<SrcChn> channels = pDevice.getChannels();
                                    for (SrcChn chl : channels) {
                                        chl.setOnline(0);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 更新设备通道在线状态
     *
     * @param puid    设备ID
     * @param srcChns 设备通道列表
     */
    public synchronized void updateDeviceChnStatus(String puid, List<SrcChns> srcChns) {
            for (List<PDevice> devices : devicesByGroup.values()) {
                synchronized (devices) {
                    for (PDevice pDevice : devices) {
                        if (puid.equals(pDevice.getPuId())) {
                            try {
                                pDevice.updateChn(srcChns);
                            } catch (Exception e) {
                                log.error("==========更新设备通道在线状态失败:{},puId:{}", e, puid);
                            }
                        }
                    }
                }
            }
    }

    /**
     * 更新设备录像在线状态
     *
     * @param puid 设备ID
     * @param recs 录像状态列表
     */
    public synchronized void updateDeviceChnRecStatus(String puid, List<Rec> recs) {
            for (List<PDevice> devices : devicesByGroup.values()) {
                synchronized (devices) {
                    for (PDevice pDevice : devices) {
                        if (puid.equals(pDevice.getPuId())) {
                            pDevice.updateChnRec(recs);
                        }
                    }
                }
            }
    }

    public boolean isLoadComplete() {
        return loadComplete;
    }

    public void setLoadComplete(boolean loadComplete) {
        this.loadComplete = loadComplete;
    }

    /**
     * 返回所有分组
     *
     * @return
     */
    public List<PGroup> getGroups() {
        List<PGroup> list = new ArrayList<PGroup>(20);
        list.addAll(this.sortGroups);
        return list;
    }

    /**
     * 返回所有分组
     *
     * @return
     */
    public List<PGroup> getAllGroups() {
        List<PGroup> list = new ArrayList<PGroup>();
        for (PGroup group : this.groups.values()) {
            list.add(group);
        }

        return list;
    }

    /**
     * 返回所有设备
     *
     * @return
     */
    public List<PDevice> getDevices() {
        List<PDevice> list = new ArrayList<PDevice>(20);
        /*for (ArrayList<PDevice> devices : devicesByGroup.values()) {
            list.addAll(devices);
        }*/
        Collection<List<PDevice>> values = devicesByGroup.values();
        Iterator<List<PDevice>> iterator = values.iterator();
        while (iterator.hasNext()) {
            List<PDevice> next = iterator.next();
            list.addAll(next);
        }
        return list;
    }

    /**
     * 返回所有设备(包含未分组设备)
     *
     * @return
     */
    public List<PDevice> getAllDevices() {
        List<PDevice> list = new ArrayList<PDevice>(20);
        for (PDevice device : devices.values()) {
            list.add(device);
        }
        return list;
    }

    /**
     * 获取指定分组的设备
     *
     * @param groupId
     * @return
     */
    public List<PDevice> getDeivcesByGroupId(String groupId) {
        List<PDevice> devices = this.devicesByGroup.get(groupId);
        ArrayList<PDevice> list = new ArrayList<PDevice>();
        if (devices != null) {
            list.addAll(devices);
        }
        return list;
    }

    /**
     * 获取监控平台“内置根分组”的ID
     *
     * @return
     */
    public String getRootGroupId() {
        return rootGroupId;
    }

    /**
     * 中文字符串大小比较
     *
     * @param s1
     * @param s2
     * @return
     */
    public static int compareZH_CN(String s1, String s2) {
        if (s1 == null || s2 == null) return 0;
        return Collator.getInstance(Locale.CHINA).compare(s1, s2);
    }

    /**
     * 递归得到分组设备总数
     *
     * @param pGroup
     */
    public void deviceCount(PGroup pGroup) {
        List<PGroup> sortChildGroups = pGroup.getSortChildGroups();
        //递归条件从上往下找直到该分组底下没有子分组时就是递归最底层在此之前都递归调用方法本身
        Iterator<PGroup> iterator = sortChildGroups.iterator();
        while (iterator.hasNext()) {
            PGroup next = iterator.next();
            deviceCount(next);
        }
        //到最低下一层是没有子分组的所以分组设备数等于该分组所属设备总数，但是往上一层的话该分组设备数等于该分组所属设备数加上其子分组设备数
        List<PDevice> deivces = this.getDeivcesByGroupId(pGroup.getId());
        Integer deviceOnLineCount = 0;
        //得到设备在线总数
        Integer deviceOnLineCountAfter = getDeviceOnLineCount(deivces, deviceOnLineCount);
        //计算通道在线总数
        chnOnLineCount(deivces);
        List<PGroup> groups = pGroup.getSortChildGroups();
        //遍历子分组集合
        if (CollectionUtil.isNotEmpty(groups)) {
            Iterator<PGroup> pGroupIterator = groups.iterator();
            Integer groupCount = 0;
            Integer deviceOnLine = 0;
            while (pGroupIterator.hasNext()) {
                PGroup next = pGroupIterator.next();
                List<PDevice> deivceInside = this.getDeivcesByGroupId(next.getId());
                //累加子分组设备在线总数
                deviceOnLine = getDeviceOnLineCount(deivceInside, 0) + deviceOnLine;
                //累加子分组设备总数
                groupCount = deivceInside.size() + groupCount;
                //计算通道在线总数
                chnOnLineCount(deivceInside);
            }
            //当分组下存在子分组时设备总数等于该分组所属设备数＋子分组设备数
            pGroup.setCount(deivces.size() + groupCount);
            //当分组下存在子分组时设备在线总数等于该分组所属设备在线数＋子分组设备在线数
            pGroup.setOnLineCount(deviceOnLineCountAfter + groupCount);
        } else {
            pGroup.setCount(deivces.size());
            pGroup.setOnLineCount(deviceOnLineCountAfter);
        }
    }

    /**
     * 计算设备总在线数
     *
     * @param devices
     * @param onLineCount
     * @return
     */
    private Integer getDeviceOnLineCount(List<PDevice> devices, Integer onLineCount) {
        Iterator<PDevice> iterator = devices.iterator();
        while (iterator.hasNext()) {
            PDevice next = iterator.next();
            if (next.getOnline().equals(1)) {
                onLineCount = onLineCount + 1;
            }
        }
        return onLineCount;
    }

    /**
     * 计算设备通道在线数
     *
     * @param devices
     */
    private void chnOnLineCount(List<PDevice> devices) {
        Iterator<PDevice> iterator = devices.iterator();
        while (iterator.hasNext()) {
            PDevice next = iterator.next();
            Integer onLineCount = 0;
            List<SrcChn> channels = next.getChannels();
            if (CollectionUtil.isNotEmpty(channels)) {
                for (SrcChn channel : channels) {
                    if (channel.getOnline().equals(1)) {
                        onLineCount = onLineCount + 1;
                    }
                }
            }
            next.setOnLineCount(onLineCount);
        }
    }


    public Hashtable<String, PChannelStatus> getStatus_map() {
        return status_map;
    }

    public void setStatus_map(Hashtable<String, PChannelStatus> status_map) {
        this.status_map = status_map;
    }

}
