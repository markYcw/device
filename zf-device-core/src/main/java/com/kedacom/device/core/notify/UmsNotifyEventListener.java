package com.kedacom.device.core.notify;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.kedacom.device.core.constant.Event;
import com.kedacom.device.core.convert.UmsGroupConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.entity.GroupInfoEntity;
import com.kedacom.device.core.entity.SubDeviceInfoEntity;
import com.kedacom.device.core.event.DeviceAndGroupEvent;
import com.kedacom.device.core.event.DeviceEvent;
import com.kedacom.device.core.event.DeviceGroupEvent;
import com.kedacom.device.core.event.DeviceStateEvent;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.mapper.GroupMapper;
import com.kedacom.device.core.mapper.SubDeviceMapper;
import com.kedacom.device.ums.DeviceGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/17
 */
@Slf4j
@Component
public class UmsNotifyEventListener {

    @Resource
    GroupMapper groupMapper;

    @Resource
    DeviceMapper deviceMapper;

    @Resource
    SubDeviceMapper subDeviceMapper;

    private final ConcurrentHashMap<String, NotifyCallback> listenerMap = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, List<GroupInfoEntity>> deviceGroupMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> num = new ConcurrentHashMap<>();

    public void setListenerCallback(String callbackName, NotifyCallback listenerCallback) {
        listenerMap.put(callbackName, listenerCallback);
    }

    @EventListener(DeviceGroupEvent.class)
    public void deviceGroupNotify(DeviceGroupEvent deviceGroupEvent) {
        log.info("deviceGroupNotify 设备分组通知:{}", deviceGroupEvent);
        List<DeviceGroupVo> result = deviceGroupEvent.getResult();
        if (CollectionUtil.isEmpty(result)) {
            log.error("获取设备分组通知信息为空");
        }
        Integer ssid = deviceGroupEvent.getNty().getSsid();
        LambdaQueryWrapper<DeviceInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceInfoEntity::getSessionId, ssid);
        String umsId = deviceMapper.selectOne(wrapper).getId();
        List<GroupInfoEntity> entityList = UmsGroupConvert.INSTANCE.convertGroupInfoEntityList(result);
        entityList.forEach(entity -> entity.setGroupDevId(umsId));
        List<GroupInfoEntity> groupInfoEntities = deviceGroupMap.get(ssid + "_" + deviceGroupEvent.getNty().getSsno());
        // 存入内存
        if (CollectionUtil.isNotEmpty(groupInfoEntities)) {
            groupInfoEntities.addAll(entityList);
            deviceGroupMap.put(ssid + "_" + deviceGroupEvent.getNty().getSsno(), groupInfoEntities);
        } else {
            deviceGroupMap.put(ssid + "_" + deviceGroupEvent.getNty().getSsno(), entityList);
        }
        try {
            if (deviceGroupEvent.getEnd() == 1) {
                log.info("deviceGroupNotify 设备分组通知开始同步数据库count:{}.data:{}", groupInfoEntities.size(), groupInfoEntities);
                List<String> groupIds = groupInfoEntities.stream().map(GroupInfoEntity::getGroupId).collect(Collectors.toList());
                log.info("deviceGroupNotify 设备分组通知开始同步数据库-分组id:{}", groupIds);
                LambdaQueryWrapper<GroupInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
                LambdaUpdateWrapper<GroupInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
                // 同步分组
                for (GroupInfoEntity umsGroupEntity : groupInfoEntities) {
                    queryWrapper.eq(GroupInfoEntity::getGroupId, umsGroupEntity.getGroupId());
                    GroupInfoEntity checkUmsGroup = groupMapper.selectOne(queryWrapper);
                    if (checkUmsGroup != null) {
                        updateWrapper.eq(GroupInfoEntity::getGroupId, umsGroupEntity.getGroupId());
                        groupMapper.update(umsGroupEntity, updateWrapper);
                    } else {
                        groupMapper.insert(umsGroupEntity);
                    }
                    updateWrapper.clear();
                    queryWrapper.clear();
                }
                // 查询本次同步不在notify中的分组id，并删除
                updateWrapper.clear();
                updateWrapper.notIn(GroupInfoEntity::getGroupId, groupIds);
                groupMapper.delete(updateWrapper);
                queryWrapper.clear();
                NotifyCallback notifyCallback = listenerMap.get(ssid + "_" + deviceGroupEvent.getNty().getSsno());
                if (notifyCallback != null) notifyCallback.success();
            }
        } catch (Exception e) {
            log.error("deviceGroupNotify  failure:{},retry,exception:{}", ssid + "_" + deviceGroupEvent.getNty().getSsno(), e);
            NotifyCallback notifyCallback = listenerMap.get(ssid + "_" + deviceGroupEvent.getNty().getSsno());
            if (notifyCallback != null) notifyCallback.failure();
            deviceGroupMap.clear();
            log.error("deviceGroupNotify finally failure:{}", ssid + "_" + deviceGroupEvent.getNty().getSsno());
        }

    }

    @EventListener(DeviceStateEvent.class)
    public void deviceStateNotify(DeviceStateEvent deviceStateEvent) {

        Integer operateType = deviceStateEvent.getOperateType();
        LambdaQueryWrapper<GroupInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupInfoEntity::getGroupId, deviceStateEvent.getId());
        //设备状态 - 10 ： 分组的添加修改； 11 ： 分组的删除
        if (Event.OPERATETYPE.equals(operateType)) {
            GroupInfoEntity groupInfoEntity = groupMapper.selectOne(queryWrapper);
            if (groupInfoEntity == null) {
                GroupInfoEntity entity = new GroupInfoEntity();
                entity.setGroupId(deviceStateEvent.getId());
                entity.setParentId(deviceStateEvent.getParentId());
                entity.setGroupName(deviceStateEvent.getName());
                entity.setSortIndex(deviceStateEvent.getSortIndex());
                groupMapper.insert(entity);
            } else {
                groupInfoEntity.setGroupId(deviceStateEvent.getId());
                groupInfoEntity.setGroupName(deviceStateEvent.getName());
                groupInfoEntity.setParentId(deviceStateEvent.getParentId());
                groupInfoEntity.setSortIndex(deviceStateEvent.getSortIndex());
                groupMapper.updateById(groupInfoEntity);
            }
        } else {
            groupMapper.delete(queryWrapper);
        }
    }

    @EventListener(DeviceAndGroupEvent.class)
    public void deviceStateNotify(DeviceAndGroupEvent event) {

        log.info("设备与分组关心状态变更通知 ： {}", event);
        LambdaUpdateWrapper<SubDeviceInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SubDeviceInfoEntity::getId, event.getId())
                .set(SubDeviceInfoEntity::getGbid, event.getGbid())
                .set(SubDeviceInfoEntity::getGroupId, event.getGroupId());
        subDeviceMapper.update(null, updateWrapper);

    }

    @EventListener(DeviceEvent.class)
    public void deviceStatusNotify(DeviceEvent event) {

        Integer operateType = event.getOperateType();
        //TODO 暂时只做了 设备的新增，修改，删除
        if (Event.OPERATETYPETYPE3.equals(operateType)) {
            SubDeviceInfoEntity subDeviceInfoEntity = toSubDeviceInfoEntity(event);
            subDeviceMapper.insert(subDeviceInfoEntity);
        }
        if (Event.OPERATETYPETYPE4.equals(operateType)) {
            SubDeviceInfoEntity subDeviceInfoEntity = toSubDeviceInfoEntity(event);
            subDeviceMapper.updateById(subDeviceInfoEntity);
        }
        if (Event.OPERATETYPETYPE5.equals(operateType)) {
            subDeviceMapper.deleteById(event.getId());
        }

    }

    private SubDeviceInfoEntity toSubDeviceInfoEntity(DeviceEvent event) {

        SubDeviceInfoEntity subDeviceInfoEntity = new SubDeviceInfoEntity();
        subDeviceInfoEntity.setAddress(event.getAddress());
        subDeviceInfoEntity.setCivilName(event.getCivilCode_name());
        subDeviceInfoEntity.setDepartmentName(event.getDepartmentCode_name());
        subDeviceInfoEntity.setDeviceIp(event.getIpv4());
        subDeviceInfoEntity.setDeviceType(event.getDeviceType());
        subDeviceInfoEntity.setDomainId(event.getDomainId());
        subDeviceInfoEntity.setGbid(event.getGbid());
        subDeviceInfoEntity.setGroupId(event.getGroupId());
        subDeviceInfoEntity.setId(event.getId());
        subDeviceInfoEntity.setInstallDate(event.getInstallDate());
        subDeviceInfoEntity.setLatitude(event.getLatitude());
        subDeviceInfoEntity.setLatitudeStr(event.getLatitudeStr());
        subDeviceInfoEntity.setLongitude(event.getLongitude());
        subDeviceInfoEntity.setLongitudeStr(event.getLongitudeStr());
        subDeviceInfoEntity.setMaintainContact(event.getMgtUnitContact());
        subDeviceInfoEntity.setMaintainMan(event.getMgtMan());
        subDeviceInfoEntity.setManufactorCode(event.getManufactorCode());
        subDeviceInfoEntity.setManufactorName(event.getManufactorCode_name());
        subDeviceInfoEntity.setModel(event.getModel());
        subDeviceInfoEntity.setName(event.getName());
        subDeviceInfoEntity.setParentId(event.getParentId());
        subDeviceInfoEntity.setStatus(event.getStatus());

        return subDeviceInfoEntity;
    }

}
