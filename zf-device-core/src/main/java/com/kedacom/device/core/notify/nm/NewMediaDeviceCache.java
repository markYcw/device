package com.kedacom.device.core.notify.nm;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kedacom.device.core.notify.nm.pojo.NmGroup;
import com.kedacom.device.core.notify.nm.pojo.NmGroupDeviceIds;
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
     * 新媒体平台内置根分组的ID。
     * 通过接口不能获取到内置根分组，只能通过识别“内置未分组”的parentId。
     */
    private String rootGroupId;

    /**
     * 新媒体平台上报的根分组ID
     */
    private String pubRootGroupId;

    /**
     * 分组
     * key:分组ID, value：分组信息
     */
    private Hashtable<String, NmGroup> groups = new Hashtable<String, NmGroup>(20);

    /**
     * 设备
     * key:分组ID, value：分组下的设备。
     */
    private ConcurrentHashMap<String, ArrayList<NMDevice>> devicesByGroup = new ConcurrentHashMap<>(100);

    /**
     * 设备国标ID和分组ID对应
     * key:国标ID, value：分组ID
     */
    private ConcurrentHashMap<String,String> deviceByGbId = new ConcurrentHashMap<>();

    /**
     * 清空数据
     */
    public void clear() {
        this.groups.clear();
        this.devicesByGroup.clear();
        this.deviceByGbId.clear();
    }

    /**
     * 清空所有设备
     */
    public void clearDevice() {
        this.devicesByGroup.clear();
    }

    /**
     * 添加分组集合
     * @param groups
     */
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
            //寻找当前所有分组的父分组是否为当前分组，如果是则设置子分组。
            Iterator<NmGroup> it = groups.values().iterator();
            while (it.hasNext()) {
                NmGroup temp = it.next();
                if (temp.getParentId() != null && temp.getParentId().equals(group.getId())) {
                    group.addChildGroup(temp);
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
        //先记录国标ID和分组ID的关系
        this.deviceByGbId.put(device.getGbId(),device.getGroupId());
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
     * 更新设备信息
     * @param devices
     */
    public void updateDevices(List<NMDevice> devices){
        Iterator<NMDevice> iterator = devices.iterator();
        while (iterator.hasNext()){
            NMDevice next = iterator.next();
            ArrayList<NMDevice> nmDevices = devicesByGroup.get(next.getGroupId());
            if(CollectionUtil.isNotEmpty(nmDevices)){
                Iterator<NMDevice> list = nmDevices.iterator();
                while (list.hasNext()){
                    NMDevice device = list.next();
                    if(next.getId().equals(device.getId())){
                        list.remove();
                        nmDevices.add(next);
                    }
                }
            }
        }
    }

    /**
     * 分组和设备关系变更
     * @param groupList
     */
    public void deviceGroupChange(List<NmGroupDeviceIds> groupList){
        Iterator<NmGroupDeviceIds> iterator = groupList.iterator();
        while (iterator.hasNext()){
            NmGroupDeviceIds next = iterator.next();
            String groupId = this.deviceByGbId.get(next.getGbId());
            if(StringUtils.isNotBlank(groupId)){
                ArrayList<NMDevice> nmDevices = this.devicesByGroup.get(groupId);
                if(CollectionUtil.isNotEmpty(nmDevices)){
                    Iterator<NMDevice> ite = nmDevices.iterator();
                    while (ite.hasNext()){
                        NMDevice nm = ite.next();
                        if(nm.getGbId().equals(next.getGbId())){
                            nm.setGroupId(next.getGroupId());
                            //删除原国标ID和分组ID对应关系
                            this.deviceByGbId.remove(next.getGbId());
                            //删除原有分组下设备集合存在的这个设备
                            ite.remove();
                            //把这个设备添加到新的分组下
                            this.addDevice(nm);
                        }
                    }
                }
            }
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
            if(CollectionUtil.isNotEmpty(nmDevices)){
                Iterator<NMDevice> list = nmDevices.iterator();
                while (list.hasNext()){
                    NMDevice device = list.next();
                    if(next.getId().equals(device.getId())){
                        device.setStatus(next.getStatus());
                    }
                }
            }
        }
    }


    /**
     * 删除设备
     *
     * @param devices
     */
    public void removeDevices(List<NMDevice> devices) {
        Iterator<NMDevice> iterator = devices.iterator();
        while (iterator.hasNext()){
            NMDevice next = iterator.next();
            //先删除国标ID和分组ID的关系
            this.deviceByGbId.remove(next.getGbId());
            //得到分组下设备集合然后判断设备集合里面有没有这个元素有这个元素的话就删除
            ArrayList<NMDevice> nmDevices = devicesByGroup.get(next.getGroupId());
            if(CollectionUtil.isNotEmpty(nmDevices)){
                Iterator<NMDevice> list = nmDevices.iterator();
                while (list.hasNext()){
                    NMDevice device = list.next();
                    if(next.getId().equals(device.getId())){
                        list.remove();
                    }
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
    public List<NMDevice> getDeviceByGroupId(String groupId) {
        ArrayList<NMDevice> devices = this.devicesByGroup.get(groupId);
        ArrayList<NMDevice> list = new ArrayList<NMDevice>();
        if (devices != null) {
            list.addAll(devices);
        }
        return list;
    }

    /**
     * 获取新媒体平台“内置根分组”的ID
     *
     * @return
     */
    public String getRootGroupId() {
        return rootGroupId;
    }


}
