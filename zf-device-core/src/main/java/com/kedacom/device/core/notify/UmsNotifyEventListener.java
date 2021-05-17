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
import com.kedacom.device.core.event.DeviceStateEvent;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.mapper.GroupMapper;
import com.kedacom.device.core.mapper.SubDeviceMapper;
import com.kedacom.device.ums.DeviceGroupVo;
import com.kedacom.device.core.event.DeviceGroupEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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

    @EventListener(DeviceGroupEvent.class)
    public void deviceGroupNotify(DeviceGroupEvent deviceGroupEvent) {

        List<DeviceGroupVo> result = deviceGroupEvent.getResult();
        if (CollectionUtil.isEmpty(result)) {
            log.error("获取设备分组通知信息为空");
        }
        log.info("获取设备分组通知信息 ： {}", result);
        Integer ssid = deviceGroupEvent.getNty().getSsid();
        LambdaQueryWrapper<DeviceInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceInfoEntity::getSessionId, ssid);
        String umsId = deviceMapper.selectOne(wrapper).getId();
        List<GroupInfoEntity> entityList = UmsGroupConvert.INSTANCE.convertGroupInfoEntityList(result);
        entityList.forEach(entity -> entity.setGroupDevId(umsId));

        LambdaQueryWrapper<GroupInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        LambdaUpdateWrapper<GroupInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
        for (GroupInfoEntity umsGroupEntity : entityList) {
            queryWrapper.eq(GroupInfoEntity::getGroupId, umsGroupEntity.getGroupId());
            GroupInfoEntity checkUmsGroup = groupMapper.selectOne(queryWrapper);
            if (checkUmsGroup != null) {
                updateWrapper.eq(GroupInfoEntity::getGroupId, umsGroupEntity.getGroupId());
                groupMapper.update(umsGroupEntity, updateWrapper);
                updateWrapper.clear();
            }else {
                groupMapper.insert(umsGroupEntity);
            }
            queryWrapper.clear();
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
