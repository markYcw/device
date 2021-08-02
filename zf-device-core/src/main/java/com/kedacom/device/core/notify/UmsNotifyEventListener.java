package com.kedacom.device.core.notify;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.kedacom.core.DeviceStatusListenerManager;
import com.kedacom.device.core.constant.Event;
import com.kedacom.device.core.convert.UmsGroupConvert;
import com.kedacom.device.core.convert.UmsSubDeviceConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.entity.GroupInfoEntity;
import com.kedacom.device.core.entity.SubDeviceInfoEntity;
import com.kedacom.device.core.event.*;
import com.kedacom.device.core.kafka.UmsKafkaMessageProducer;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.mapper.GroupMapper;
import com.kedacom.device.core.mapper.SubDeviceMapper;
import com.kedacom.device.ums.DeviceGroupVo;
import com.kedacom.ums.entity.UmsSubDeviceChangeModel;
import com.kedacom.ums.entity.UmsSubDeviceStatusModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;
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

    @Resource
    UmsKafkaMessageProducer umsKafkaMessageProducer;

    private ExecutorService executorService = new ThreadPoolExecutor(2, 5, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(20));

    private final ConcurrentHashMap<String, NotifyCallback> listenerMap = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, List<GroupInfoEntity>> deviceGroupMap = new ConcurrentHashMap<>();

    public void setListenerCallback(String callbackName, NotifyCallback listenerCallback) {
        listenerMap.put(callbackName, listenerCallback);
    }

    @Async
    @EventListener(DeviceGroupEvent.class)
    public void deviceGroupNotify(DeviceGroupEvent deviceGroupEvent) {
        if (log.isDebugEnabled()) {
            log.info("deviceGroupNotify 设备分组通知:{}", deviceGroupEvent);
        }
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
                groupInfoEntities = deviceGroupMap.get(ssid + "_" + deviceGroupEvent.getNty().getSsno());
                List<String> groupIds = groupInfoEntities.stream().map(GroupInfoEntity::getGroupId).collect(Collectors.toList());
                if (log.isDebugEnabled()) {
                    log.info("deviceGroupNotify 设备分组通知开始同步数据库count:{}.data:{}", groupInfoEntities.size(), groupInfoEntities);
                }
                log.info("deviceGroupNotify 设备分组通知开始同步数据库-总量:{},分组id:{}", groupIds.size(), groupIds);
                LambdaQueryWrapper<GroupInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
                LambdaUpdateWrapper<GroupInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
                // 同步分组
                for (GroupInfoEntity umsGroupEntity : groupInfoEntities) {
                    queryWrapper.eq(GroupInfoEntity::getGroupDevId, umsId)
                            .eq(GroupInfoEntity::getGroupId, umsGroupEntity.getGroupId());
                    GroupInfoEntity checkUmsGroup = groupMapper.selectOne(queryWrapper);
                    if (checkUmsGroup != null) {
                        umsGroupEntity.setId(checkUmsGroup.getId());
                        groupMapper.updateById(umsGroupEntity);
//                        updateWrapper.eq(GroupInfoEntity::getGroupId, umsGroupEntity.getGroupId());
//                        groupMapper.update(umsGroupEntity, updateWrapper);
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
                if (notifyCallback != null) {
                    notifyCallback.success();
                }
            }
        } catch (Exception e) {
            log.error("deviceGroupNotify  failure:{},retry,exception:{}", ssid + "_" + deviceGroupEvent.getNty().getSsno(), e);
            NotifyCallback notifyCallback = listenerMap.get(ssid + "_" + deviceGroupEvent.getNty().getSsno());
            if (notifyCallback != null) {
                notifyCallback.failure();
            }
            deviceGroupMap.clear();
            log.error("deviceGroupNotify finally failure:{}", ssid + "_" + deviceGroupEvent.getNty().getSsno());
        }

    }

    @EventListener(DeviceGroupStateEvent.class)
    public void deviceGroupStateNotify(DeviceGroupStateEvent event) {
        log.info("设备分组状态变更通知:{}", event);
        Integer ssid = event.getNty().getSsid();
        LambdaQueryWrapper<DeviceInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceInfoEntity::getSessionId, ssid);
        String umsId = deviceMapper.selectOne(wrapper).getId();
        Integer operateType = event.getOperateType();
        LambdaQueryWrapper<GroupInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupInfoEntity::getGroupId, event.getId());
        //设备状态 - 10 ： 分组的添加修改； 11 ： 分组的删除
        if (Event.OPERATETYPE.equals(operateType)) {
            GroupInfoEntity groupInfoEntity = groupMapper.selectOne(queryWrapper);
            if (groupInfoEntity == null) {
                GroupInfoEntity entity = new GroupInfoEntity();
                entity.setGroupDevId(umsId);
                entity.setGroupId(event.getId());
                entity.setParentId(event.getParentId());
                entity.setGroupName(event.getName());
                entity.setSortIndex(event.getSortIndex());
                groupMapper.insert(entity);
            } else {
                groupInfoEntity.setGroupDevId(umsId);
                groupInfoEntity.setGroupId(event.getId());
                groupInfoEntity.setGroupName(event.getName());
                groupInfoEntity.setParentId(event.getParentId());
                groupInfoEntity.setSortIndex(event.getSortIndex());
                groupMapper.updateById(groupInfoEntity);
            }
        } else {
            groupMapper.delete(queryWrapper);
        }
    }

    @EventListener(DeviceAndGroupEvent.class)
    public void deviceStateNotify(DeviceAndGroupEvent event) {
        List<DevAndGroup> devAndGroupList = event.getDevandgroup();
        log.info("设备与分组关联状态变更通知 - devAndGroupList : [{}]", devAndGroupList);
        for (DevAndGroup devAndGroup : devAndGroupList) {
            LambdaUpdateWrapper<SubDeviceInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(SubDeviceInfoEntity::getGbid, devAndGroup.getGbid())
                    .set(SubDeviceInfoEntity::getGroupId, devAndGroup.getGroupId());
            subDeviceMapper.update(null, updateWrapper);
        }
    }

    @EventListener(DeviceStateEvent.class)
    public void deviceStatusNotify(DeviceStateEvent event) {
        log.info("设备状态变更通知:{}", event);
        if (event == null) {
            log.error("设备状态变更通知为空");
            return;
        }
        Integer ssid = event.getNty().getSsid();
        LambdaQueryWrapper<DeviceInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeviceInfoEntity::getSessionId, ssid);
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectOne(queryWrapper);
        String umsId = deviceInfoEntity.getId();
        Integer operateType = event.getOperateType();
        //设备状态改变
        if (Event.OPERATETYPETYPE1.equals(operateType)) {
            LambdaUpdateWrapper<SubDeviceInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(SubDeviceInfoEntity::getDeviceId, event.getId())
                    .eq(SubDeviceInfoEntity::getParentId, umsId)
                    .set(SubDeviceInfoEntity::getDeviceStatus, event.getStatus());
            subDeviceMapper.update(null, updateWrapper);
            UmsSubDeviceStatusModel umsSubDeviceStatusModel = UmsSubDeviceStatusModel.builder()
                    .devId(event.getId())
                    .parentId(umsId)
                    .devStatus(event.getStatus())
                    .timeStamp(System.currentTimeMillis())
                    .build();

            sendKafkaOfDeviceStatus(umsSubDeviceStatusModel);
            DeviceStatusListenerManager.getInstance().publish(umsSubDeviceStatusModel);
        }
        if (Event.OPERATETYPETYPE4.equals(operateType)) {
            SubDeviceInfoEntity subDeviceInfoEntity = toSubDeviceInfoEntity(event);
            subDeviceInfoEntity.setParentId(umsId);
            LambdaQueryWrapper<SubDeviceInfoEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SubDeviceInfoEntity::getParentId, umsId)
                    .eq(SubDeviceInfoEntity::getGbid, subDeviceInfoEntity.getGbid());
            SubDeviceInfoEntity entity = subDeviceMapper.selectOne(wrapper);
            if (entity == null) {
                //设备新增
                subDeviceMapper.insert(subDeviceInfoEntity);
                UmsSubDeviceChangeModel umsSubDeviceChangeModel = UmsSubDeviceConvert.INSTANCE.convertUmsSubDeviceChangeModel(subDeviceInfoEntity);
                umsSubDeviceChangeModel.setNtyType(0);
                sendKafkaOfDeviceChange(umsSubDeviceChangeModel);
                DeviceStatusListenerManager.getInstance().publishDeviceChange(umsSubDeviceChangeModel);
            } else {
                //设备修改
                subDeviceInfoEntity.setId(entity.getId());
                subDeviceMapper.updateById(subDeviceInfoEntity);
                UmsSubDeviceChangeModel umsSubDeviceChangeModel = UmsSubDeviceConvert.INSTANCE.convertUmsSubDeviceChangeModel(subDeviceInfoEntity);
                umsSubDeviceChangeModel.setNtyType(1);
                sendKafkaOfDeviceChange(umsSubDeviceChangeModel);
                DeviceStatusListenerManager.getInstance().publishDeviceChange(umsSubDeviceChangeModel);
            }
        }
        //设备删除
        if (Event.OPERATETYPETYPE5.equals(operateType)) {
            String gbid = event.getGbid();
            LambdaQueryWrapper<SubDeviceInfoEntity> deleteWrapper = new LambdaQueryWrapper<>();
            deleteWrapper.eq(SubDeviceInfoEntity::getGbid, gbid);
            subDeviceMapper.delete(deleteWrapper);
            SubDeviceInfoEntity subDeviceInfoEntity = toSubDeviceInfoEntity(event);
            UmsSubDeviceChangeModel umsSubDeviceChangeModel = UmsSubDeviceConvert.INSTANCE.convertUmsSubDeviceChangeModel(subDeviceInfoEntity);
            umsSubDeviceChangeModel.setNtyType(2);
            sendKafkaOfDeviceChange(umsSubDeviceChangeModel);
            DeviceStatusListenerManager.getInstance().publishDeviceChange(umsSubDeviceChangeModel);
        }
    }

    @EventListener(ScheduleStatusEvent.class)
    public void scheduleStatusNotify(ScheduleStatusEvent event) {

        log.info("调度组状态通知信息：{}, 设备所有资源节点: {}, 设备下级的真实资源节点:{}", event, event.getMediaResourceNodeInfo(), event.getRealMediaResourceNodeInfo());
        sendScheduleStatus(event);

    }

    private void sendKafkaOfDeviceStatus(UmsSubDeviceStatusModel umsSubDeviceStatusModel) {
        executorService.submit(() -> {
            umsKafkaMessageProducer.deviceStatusUpdate(umsSubDeviceStatusModel.toString()).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.error("ID为{}的设备状态变更消息发送失败，失败信息为：{}", umsSubDeviceStatusModel.getDevId(), throwable);
                }

                @Override
                public void onSuccess(SendResult<Object, Object> objectObjectSendResult) {
                    log.info("ID为{}的设备状态变更消息发送成功，成功信息为：{}", umsSubDeviceStatusModel.getDevId(), objectObjectSendResult);
                }
            });
        });

    }

    private void sendKafkaOfDeviceChange(UmsSubDeviceChangeModel umsSubDeviceChangeModel) {
        executorService.submit(() -> {
            umsKafkaMessageProducer.deviceChange(umsSubDeviceChangeModel.toString()).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.error("ID为{}的设备状态变更消息发送失败，失败信息为：{}", umsSubDeviceChangeModel.getDeviceId(), throwable);
                }

                @Override
                public void onSuccess(SendResult<Object, Object> objectObjectSendResult) {
                    log.info("ID为{}的设备状态变更消息发送成功，成功信息为：{}", umsSubDeviceChangeModel.getDeviceId(), objectObjectSendResult);
                }
            });
        });

    }

    private void sendScheduleStatus(ScheduleStatusEvent scheduleStatusEvent) {
        executorService.submit(() -> {
            umsKafkaMessageProducer.scheduleStatusUpdate(scheduleStatusEvent.toString()).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.error("调度组ID为{}的状态变更消息发送失败，失败信息为：{}", scheduleStatusEvent.getGroupID(), throwable);
                }

                @Override
                public void onSuccess(SendResult<Object, Object> objectObjectSendResult) {
                    log.info("调度组ID为{}的状态变更消息发送成功，成功信息为：{}", scheduleStatusEvent.getGroupID(), objectObjectSendResult);
                }
            });
        });
    }

    private SubDeviceInfoEntity toSubDeviceInfoEntity(DeviceStateEvent event) {

        SubDeviceInfoEntity subDeviceInfoEntity = new SubDeviceInfoEntity();
        subDeviceInfoEntity.setAddress(event.getAddress());
        subDeviceInfoEntity.setCivilName(event.getCivilCode_name());
        subDeviceInfoEntity.setDepartmentName(event.getDepartmentCode_name());
        subDeviceInfoEntity.setDeviceIp(event.getIpv4());
        subDeviceInfoEntity.setDeviceType(event.getDeviceType());
        subDeviceInfoEntity.setDomainId(event.getDomainId());
        subDeviceInfoEntity.setGbid(event.getGbid());
        subDeviceInfoEntity.setGroupId(event.getGroupId());
        subDeviceInfoEntity.setDeviceId(event.getId());
        subDeviceInfoEntity.setInstallDate(null);
        subDeviceInfoEntity.setLatitude(event.getLatitude());
        subDeviceInfoEntity.setLatitudeStr(event.getLatitudeStr());
        subDeviceInfoEntity.setLongitude(event.getLongitude());
        subDeviceInfoEntity.setLongitudeStr(event.getLongitudeStr());
        subDeviceInfoEntity.setMaintainContact(event.getMgtUnitContact());
        subDeviceInfoEntity.setMaintainMan(event.getMgtMan());
        subDeviceInfoEntity.setManufactorCode(event.getManufactorCode());
        subDeviceInfoEntity.setManufactorName(event.getManufactorCode_name());
        subDeviceInfoEntity.setDeviceModel(event.getModel());
        subDeviceInfoEntity.setName(event.getName());
        subDeviceInfoEntity.setParentId(event.getParentId());
        subDeviceInfoEntity.setDeviceStatus(event.getStatus());

        return subDeviceInfoEntity;
    }

}
