package com.kedacom.device.core.notify.nm;

import com.kedacom.device.core.notify.nm.pojo.NmGroup;
import com.kedacom.newMedia.pojo.NMDevice;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 新媒体设备缓存。
 *
 * @author ycw
 * @date 2022/04/07
 */
@Slf4j
@Data
public class NewMediaDeviceCache {


    private static volatile NewMediaDeviceCache instance;

    private NewMediaDeviceCache() {
    }

    public static NewMediaDeviceCache getInstance() {
        if (null == instance) {
            synchronized (NewMediaDeviceCache.class) {
                if (null == instance) {
                    instance = new NewMediaDeviceCache();
                }
            }
        }
        return instance;
    }

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
    private Hashtable<String, NmGroup> groups = new Hashtable<String, NmGroup>(20);

    /**
     * 设备
     * key:分组ID, value：分组下的设备，根据名称排序。
     */
    private ConcurrentHashMap<String, ArrayList<NMDevice>> devicesByGroup = new ConcurrentHashMap<>(100);


    /**
     * 设备是否加载完成
     */
    private boolean loadComplete = false;

    /**
     * 清空数据
     */
    public void clear() {
        this.groups.clear();
        this.devicesByGroup.clear();
    }

    /**
     * 清空数据
     */
    public void clearDevice() {
        this.devicesByGroup.clear();
    }

    //添加分组集合
    public void addDeviceGroups(Collection<NmGroup> groups) {
        for (NmGroup g : groups) {
            this.addDeviceGroup(g);
        }
    }

    /**
     * 增加分组
     *
     * @param group
     */
    public void addDeviceGroup(NmGroup group) {
        if (group.isRootGroup(group)) {
            /*
             * 查找根分组
             */
            this.rootGroupId = group.getId();
        }
        this.groups.put(group.getId(), group);
        //设置子分组
        checkSortRootGroups(group);
        //判断其是否有父分组，如果有的话把其设置在父分组底下
        NmGroup parent = groups.get(group.getParentId());
        if (parent != null) {
            parent.addChildGroup(group);
        }
    }

    //收到删除分组通知以后删除分组
    public void deleteDeviceGroups(Collection<NmGroup> groups) {
        for (NmGroup g : groups) {
            groups.remove(g.getId());
        }
    }

    //判断当前分组是否有子分组并设置子分组
    private void checkSortRootGroups(NmGroup group) {
        synchronized (groups) {
            if (group == null)
                return;
            //寻找当前所有根分组的父分组是否为group分组，如果是，则加为group的子分组，并从根分组中删除。
            Iterator<NmGroup> it = groups.values().iterator();
            while (it.hasNext()) {
                NmGroup temp = it.next();
                if (temp.getParentId() != null && temp.getParentId().equals(group.getId())) {
                    group.addChildGroup(temp);
                    it.remove();
                }
            }
        }
    }

    /**
     * 根据分组ID获取分组信息
     *
     * @param groupId
     * @return
     */
    public NmGroup getPGroupById(String groupId) {
        return groups.get(groupId);
    }



    /**
     * 增加设备
     *
     * @param device
     */
    public void addDevice(NMDevice device) {
        String groupId = device.getGroupId();
        ArrayList<NMDevice> list = this.devicesByGroup.get(groupId);
        if (list == null) {
            list = new ArrayList<NMDevice>();
            this.devicesByGroup.put(groupId, list);
        }else {
            list.add(device);
        }
    }

    public void addDevices(Collection<NMDevice> devices) {
        for (NMDevice device : devices) {
            this.addDevice(device);
        }
    }

    /**
     * 更新设备在线状态
     * @param devices
     */
    public void updateDeviceStatus(List<NMDevice> devices){
        Iterator<NMDevice> iterator = devices.iterator();
        while (iterator.hasNext()){
            NMDevice next = iterator.next();
            ArrayList<NMDevice> nmDevices = devicesByGroup.get(next.getGroupId());
            Iterator<NMDevice> list = nmDevices.iterator();
            while (list.hasNext()){
                NMDevice device = list.next();
                if(next.getId().equals(device.getId())){
                    device.setStatus(next.getStatus());
                }
            }
        }
    }


    /**
     * 删除设备
     *
     * @param device
     */
    public void removeDevice(NMDevice device) {
        synchronized (deviceLock) {
            ArrayList<NMDevice> devices = this.devicesByGroup.get(device.getGroupId());
            Iterator<NMDevice> iterator = devices.iterator();
            while (iterator.hasNext()){
                NMDevice next = iterator.next();
                if (next.getId().equals(device.getId())){
                    iterator.remove();
                }
            }
        }
    }


    /**
     * 返回所有分组
     *
     * @return
     */
    public List<NmGroup> getAllGroups() {
        List<NmGroup> list = new ArrayList<NmGroup>();
        for (NmGroup group : this.groups.values()) {
            list.add(group);
        }

        return list;
    }

    /**
     * 返回所有设备
     *
     * @return
     */
    public List<NMDevice> getDevices() {
        LinkedList<NMDevice> list = new LinkedList<>();
        for (ArrayList<NMDevice> devices : devicesByGroup.values()) {
            list.addAll(devices);
        }
        return list;
    }


    /**
     * 获取指定分组的设备
     *
     * @param groupId
     * @return
     */
    public List<NMDevice> getDeivcesByGroupId(String groupId) {
        ArrayList<NMDevice> devices = this.devicesByGroup.get(groupId);
        ArrayList<NMDevice> list = new ArrayList<NMDevice>();
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


}
