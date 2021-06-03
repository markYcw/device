package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kedacom.BasePage;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.constant.UmsMod;
import com.kedacom.device.core.convert.UmsAlarmTypeConvert;
import com.kedacom.device.core.convert.UmsDeviceConvert;
import com.kedacom.device.core.convert.UmsGroupConvert;
import com.kedacom.device.core.entity.AlarmTypeEntity;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.entity.GroupInfoEntity;
import com.kedacom.device.core.entity.SubDeviceInfoEntity;
import com.kedacom.device.core.exception.UmsManagerException;
import com.kedacom.device.core.mapper.AlarmTypeMapper;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.mapper.GroupMapper;
import com.kedacom.device.core.mapper.SubDeviceMapper;
import com.kedacom.device.core.notify.NotifyCallback;
import com.kedacom.device.core.notify.UmsNotifyEventListener;
import com.kedacom.device.core.runner.UmsDeviceRunner;
import com.kedacom.device.core.service.DeviceManagerService;
import com.kedacom.device.core.task.UmsDeviceTask;
import com.kedacom.device.core.task.UmsNotifyQueryTask;
import com.kedacom.device.core.utils.HandleResponseUtil;
import com.kedacom.device.core.utils.PinYinUtils;
import com.kedacom.device.ums.UmsClient;
import com.kedacom.device.ums.request.QueryAllDeviceGroupRequest;
import com.kedacom.device.ums.response.QueryAllDeviceGroupResponse;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author wangxy
 * @describe
 * @date 2021/6/1
 */
@Slf4j
@Service
public class DeviceManagerServiceImpl implements DeviceManagerService {

    @Resource
    UmsClient umsClient;

    @Resource
    GroupMapper groupMapper;

    @Resource
    DeviceMapper deviceMapper;

    @Resource
    SubDeviceMapper subDeviceMapper;

    @Resource
    AlarmTypeMapper alarmTypeMapper;

    @Autowired
    private UmsNotifyEventListener listener;

    @Autowired
    private HandleResponseUtil responseUtil;

    private final ConcurrentHashMap<String, UmsNotifyQueryTask> map = new ConcurrentHashMap<>();

    @Override
    public void syncDeviceData(UmsDeviceInfoSyncRequestDto requestDto) {

        String umsId = requestDto.getUmsId();
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(umsId);
        if (deviceInfoEntity == null) {
            log.error("未查询到统一设备信息，统一设备ID:{}", umsId);
            throw new UmsManagerException("未查询到统一设备信息");
        }

        //查询最近一次的更新时间
        String lastSyncThirdDeviceTime = deviceInfoEntity.getLastSyncThirdDeviceTime();
        SyncSubDeviceRecord syncRecord = SyncSubDeviceRecord.getInstance();
        Map<String, SyncSubDeviceRecord.Sync> record = syncRecord.getRecord();
        if (!record.containsKey(umsId)) {
            SyncSubDeviceRecord.Sync sync = new SyncSubDeviceRecord.Sync();
            sync.setUmsId(umsId);
            sync.setLastSyncTime(lastSyncThirdDeviceTime);
            record.put(umsId, sync);
        } else {
            //记录的上一次更新时间
            String recordLastSyncTime = record.get(umsId).getLastSyncTime();

            //如果记录的上一次更新时间和查询的最近一次更新时间一致，则表明上一次更新还没有完成
            //此时再发来更新请求，应该直接拒绝
            if (lastSyncThirdDeviceTime == null || lastSyncThirdDeviceTime.equals(recordLastSyncTime)) {
                log.error("当前统一设备数据正在同步中，统一设备ID:{}", umsId);
                throw new UmsManagerException("当前统一设备数据正在同步中");
            }
            //设置最新上次更新时间
            record.get(umsId).setLastSyncTime(lastSyncThirdDeviceTime);
        }

        UmsDeviceTask task = new UmsDeviceTask(umsId);
        task.run();

    }

    @Override
    public String getUmsLastSyncTime(UmsDeviceInfoSyncRequestDto requestDto) {

        String umsId = requestDto.getUmsId();
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(umsId);
        if (deviceInfoEntity == null) {
            log.error("查询统一平台信息为空 - umsId : [{}]", umsId);
        }

        return deviceInfoEntity.getLastSyncThirdDeviceTime();
    }

    @Override
    public BasePage<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceList(UmsSubDeviceInfoQueryRequestDto requestDto) {

        Page<SubDeviceInfoEntity> page = new Page<>();
        page.setCurrent(requestDto.getCurPage());
        page.setSize(requestDto.getPageSize());

        LambdaQueryWrapper<SubDeviceInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        String umsId = requestDto.getUmsId();
        if (StrUtil.isNotBlank(umsId)) {
            queryWrapper.eq(SubDeviceInfoEntity::getParentId, umsId);
        }
        String deviceName = requestDto.getDeviceName();
        if (StrUtil.isNotBlank(deviceName)) {
            if (PinYinUtils.checkString(deviceName)) {
                deviceName = PinYinUtils.StrToLowerCase(deviceName);
            }
            String finalDeviceName = deviceName;
            queryWrapper.and(query -> query.like(SubDeviceInfoEntity::getName, finalDeviceName).or().like(SubDeviceInfoEntity::getPinyin, finalDeviceName));
        }
        List<String> deviceTypeList = requestDto.getDeviceTypeList();
        if (CollectionUtil.isNotEmpty(deviceTypeList)) {
            queryWrapper.in(SubDeviceInfoEntity::getDeviceType, deviceTypeList);
        }
        String deviceIp = requestDto.getDeviceIp();
        if (StrUtil.isNotBlank(deviceIp)) {
            queryWrapper.eq(SubDeviceInfoEntity::getDeviceIp, deviceIp);
        }
        String gbId = requestDto.getGbId();
        if (StrUtil.isNotBlank(gbId)) {
            queryWrapper.eq(SubDeviceInfoEntity::getGbid, gbId);
        }
        String groupId = requestDto.getGroupId();
        if (StrUtil.isNotBlank(groupId)) {
            queryWrapper.like(SubDeviceInfoEntity::getGroupId, groupId);
        }
        Integer status = requestDto.getStatus();
        if (status != null) {
            queryWrapper.eq(SubDeviceInfoEntity::getDeviceStatus, status);
        }
        queryWrapper.orderByAsc(SubDeviceInfoEntity::getCreateTime).orderByAsc(SubDeviceInfoEntity::getGbid);
        Page<SubDeviceInfoEntity> entityPage = subDeviceMapper.selectPage(page, queryWrapper);
        List<SubDeviceInfoEntity> records = entityPage.getRecords();
        if (CollectionUtil.isEmpty(records)) {
            log.error("查询平台下挂载的子设备为空");
            return null;
        }
        log.info("查询统一平台下挂载的子设备信息 ： records [{}]", records);
        List<UmsSubDeviceInfoQueryResponseDto> umsSubDeviceInfoQueryResponseDtoList = UmsDeviceConvert.INSTANCE.convertUmsSubDeviceInfoQueryResponseDtoList(records);

        BasePage<UmsSubDeviceInfoQueryResponseDto> basePage = new BasePage<>();
        basePage.setTotal(entityPage.getTotal());
        basePage.setTotalPage(entityPage.getPages());
        basePage.setCurPage(requestDto.getCurPage());
        basePage.setPageSize(requestDto.getPageSize());
        basePage.setData(umsSubDeviceInfoQueryResponseDtoList);

        return basePage;
    }

    @Override
    public List<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceByGroupId(UmsSubDeviceInfoQueryByGroupIdRequestDto requestDto) {

        String umsId = requestDto.getUmsId();
        String groupId = requestDto.getGroupId();
        log.info("查询当前分组下挂载的子设备请求参数 ：umsId:[{}] groupId:[{}]", umsId, groupId);

        LambdaQueryWrapper<SubDeviceInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(umsId)) {
            queryWrapper.eq(SubDeviceInfoEntity::getParentId, umsId);
        }
        if (StrUtil.isNotBlank(groupId)) {
            queryWrapper.like(SubDeviceInfoEntity::getGroupId, groupId);
        } else {
            queryWrapper.and(w -> w.isNull(SubDeviceInfoEntity::getGroupId).or().eq(SubDeviceInfoEntity::getGroupId, ""));
        }
        queryWrapper.orderByAsc(SubDeviceInfoEntity::getCreateTime).orderByAsc(SubDeviceInfoEntity::getGbid);
        List<SubDeviceInfoEntity> subDeviceInfoEntityList = subDeviceMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(subDeviceInfoEntityList)) {
            log.error("查询当前分组下挂载的子设备为空");
            return null;
        }

        return UmsDeviceConvert.INSTANCE.convertUmsSubDeviceInfoQueryResponseDtoList(subDeviceInfoEntityList);
    }

    @Override
    public List<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceByIds(UmsSubDeviceInfoQueryByIdsRequestDto requestDto) {

        List<String> ids = requestDto.getIds();
        log.info("根据设备id查询设备信息参数 ： ids [{}]", ids);
        List<SubDeviceInfoEntity> subDeviceInfoEntities = subDeviceMapper.selectBatchIds(ids);
        if (CollectionUtil.isEmpty(subDeviceInfoEntities)) {
            log.error("根据设备id查询设备信息为空");
            return null;
        }
        log.info("根据设备id查询设备信息 : [{}]", subDeviceInfoEntities);

        return UmsDeviceConvert.INSTANCE.convertUmsSubDeviceInfoQueryResponseDtoList(subDeviceInfoEntities);
    }

    @Override
    public List<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceByGbIds(UmsSubDeviceInfoQueryByGbIdsRequestDto requestDto) {

        List<String> gbIds = requestDto.getGbIds();
        log.info("根据国标id查询设备信息入参 : [{}]", gbIds);
        LambdaQueryWrapper<SubDeviceInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SubDeviceInfoEntity::getGbid, gbIds);
        List<SubDeviceInfoEntity> subDeviceInfoEntities = subDeviceMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(subDeviceInfoEntities)) {
            log.error("根据国标id查询设备信息为空");
            return null;
        }
        log.info("根据国标id查询设备信息 : [{}]", subDeviceInfoEntities);

        return UmsDeviceConvert.INSTANCE.convertUmsSubDeviceInfoQueryResponseDtoList(subDeviceInfoEntities);
    }

    @Override
    public Boolean deleteUmsSubDevice(UmsSubDeviceInfoDeleteRequestDto requestDto) {

        List<String> ids = requestDto.getIds();
        log.info("删除统一平台下子设备信息入参 : [{}]", ids);
        int i = subDeviceMapper.deleteBatchIds(ids);

        return i == ids.size();
    }

    @Override
    public List<UmsAlarmTypeQueryResponseDto> selectUmsAlarmTypeList() {

        List<AlarmTypeEntity> alarmTypeEntityList = alarmTypeMapper.selectList(null);
        if (CollectionUtil.isEmpty(alarmTypeEntityList)) {
            log.error("查询告警类型信息为空");
            return null;
        }

        return UmsAlarmTypeConvert.INSTANCE.convertUmsAlarmTypeQueryResponseDtoList(alarmTypeEntityList);
    }

    @Override
    public List<UmsScheduleGroupItemQueryResponseDto> selectUmsGroupList(UmsScheduleGroupQueryRequestDto requestDto) {

        UmsScheduleGroupItemQueryRequestDto queryRequestDto = new UmsScheduleGroupItemQueryRequestDto();
        queryRequestDto.setUmsId(requestDto.getUmsId());

        return selectUmsGroupItemList(queryRequestDto);
    }

    @Override
    public List<SelectChildUmsGroupResponseDto> selectChildUmsGroupList(SelectChildUmsGroupRequestDto requestDto) {

        log.info("根据当前分组ID查询子分组集合入参 : [{}]", requestDto);
        String umsId = requestDto.getUmsId();
        String groupId = requestDto.getGroupId();
        LambdaQueryWrapper<GroupInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isBlank(groupId)) {
            groupId = "0";
        }
        queryWrapper.eq(GroupInfoEntity::getGroupDevId, umsId)
                .eq(GroupInfoEntity::getParentId, groupId)
                .orderByAsc(GroupInfoEntity::getSortIndex);
        List<GroupInfoEntity> groupInfoEntityList = groupMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(groupInfoEntityList)) {
            log.error("根据当前分组ID查询子分组集合信息为空");
            return null;
        }

        return UmsGroupConvert.INSTANCE.convertSelectChildUmsGroupResponseDtoList(groupInfoEntityList);
    }

    @Override
    public List<UmsScheduleGroupItemQueryResponseDto> selectUmsGroupItemList(UmsScheduleGroupItemQueryRequestDto requestDto) {

        long totalStartTime = System.currentTimeMillis();
        log.info("查询分组信息请求的参数为：{}", requestDto);

        String umsId = requestDto.getUmsId();
        LambdaQueryWrapper<GroupInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(umsId)) {
            queryWrapper.eq(GroupInfoEntity::getGroupDevId, umsId);
        }
        //按照字段升序
        queryWrapper.orderByAsc(GroupInfoEntity::getSortIndex);
        List<GroupInfoEntity> entityList = groupMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(entityList)) {
            return null;
        }
        List<UmsScheduleGroupItemQueryResponseDto> umsScheduleGroupItemQueryResponseDtoList = UmsGroupConvert.INSTANCE.convertUmsScheduleGroupItemQueryResponseDtoList(entityList);
        //为了sql好判断
        List<String> deviceTypeList = requestDto.getDeviceTypeList();
        if (CollectionUtil.isEmpty(deviceTypeList)) {
            requestDto.setDeviceTypeList(null);
        }
        List<UmsSubDeviceQueryDto> deviceQueryDtoList = querySubDeviceDto(requestDto);
        if (CollectionUtil.isEmpty(deviceQueryDtoList)) {
            log.error("查询子设备信息为空");
            return null;
        }
        long count = deviceQueryDtoList.stream().filter(x -> !UmsMod.EXIST.equals(x.getDeviceMod())).count();
        log.info("ordinary count is:{}", deviceQueryDtoList.size());
        log.info("filter count is {}", count);
//        Map<String, List<UmsSubDeviceQueryDto>> listMap = deviceQueryDtoList.stream().filter(x -> !UmsMod.EXIST.equals(x.getDeviceMod())).collect(Collectors.groupingBy(UmsSubDeviceQueryDto::getGroupId, Collectors.toList()));
        Map<String, List<UmsSubDeviceQueryDto>> listMap = deviceQueryDtoList.stream().collect(Collectors.groupingBy(UmsSubDeviceQueryDto::getGroupId));
        //统一设备这边一个设备可以在多个分组下，多个分组之间用“|”来分割，所以这里要处理一下
        Set<String> multiSet = listMap.keySet().stream().filter(x -> x.contains("|")).collect(Collectors.toSet());
        for (String multiGroupId : multiSet) {
            //注意 split()方法分割 | 需要转义
            String[] split = multiGroupId.split("\\|");
            for (String s : split) {
                List<UmsSubDeviceQueryDto> ordinarySubDeviceList = listMap.get(s);
                if (CollUtil.isEmpty(ordinarySubDeviceList)) {
                    ordinarySubDeviceList = new ArrayList<>();
                }
                ordinarySubDeviceList.addAll(listMap.get(multiGroupId));
                listMap.put(s, ordinarySubDeviceList);
            }
        }
        long allTotal = 0L;
        long allOnline = 0L;
        long allOfflineNum = 0L;
        long allFaultNum = 0L;
        for (UmsScheduleGroupItemQueryResponseDto responseDto : umsScheduleGroupItemQueryResponseDtoList) {
            List<UmsSubDeviceQueryDto> umsSubDeviceQueryDtoList = listMap.get(responseDto.getGroupId());
            if (CollectionUtil.isNotEmpty(umsSubDeviceQueryDtoList)) {
                int deviceTotalNum = umsSubDeviceQueryDtoList.size();
                long deviceFaultNum = umsSubDeviceQueryDtoList.stream().filter(x -> x.getStatus() == 2).count();
                long deviceOnlineNum = umsSubDeviceQueryDtoList.stream().filter(x -> x.getStatus() == 0).count();
                long deviceOfflineNum = umsSubDeviceQueryDtoList.stream().filter(x -> x.getStatus() == 1).count();

                responseDto.setDeviceTotalNum(deviceTotalNum);
                responseDto.setDeviceFaultNum((int) deviceFaultNum);
                responseDto.setDeviceOnlineNum((int) deviceOnlineNum);
                responseDto.setDeviceOfflineNum((int) deviceOfflineNum);

                allTotal += deviceTotalNum;
                allOnline += deviceOnlineNum;
                allOfflineNum += deviceOfflineNum;
                allFaultNum += allFaultNum;
            }
        }
        long totalEndTime = System.currentTimeMillis();
        log.debug("总共用时:{}", totalEndTime - totalStartTime);
        log.info("总共用时:{}", totalEndTime - totalStartTime);
        log.info("allTotal is {}", allTotal);
        log.info("allOnline is {}", allOnline);
        log.info("allOfflineNum is {}", allOfflineNum);
        log.info("allFaultNum is {}", allFaultNum);

        return umsScheduleGroupItemQueryResponseDtoList;
    }

    public List<UmsSubDeviceQueryDto> querySubDeviceDto(UmsScheduleGroupItemQueryRequestDto requestDto) {

        LambdaQueryWrapper<SubDeviceInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        String deviceName = requestDto.getDeviceName();
        if (StrUtil.isNotBlank(deviceName)) {
            if (PinYinUtils.checkString(deviceName)) {
                deviceName = PinYinUtils.StrToLowerCase(deviceName);
            }
            String finalDeviceName = deviceName;
            queryWrapper.and(query -> query.like(SubDeviceInfoEntity::getName, finalDeviceName).or().like(SubDeviceInfoEntity::getPinyin, finalDeviceName));
        }
        Integer deviceStatus = requestDto.getDeviceStatus();
        if (deviceStatus != null) {
            queryWrapper.eq(SubDeviceInfoEntity::getDeviceStatus, deviceStatus);
        }
        List<String> deviceTypeList = requestDto.getDeviceTypeList();
        if (CollectionUtil.isNotEmpty(deviceTypeList)) {
            queryWrapper.in(SubDeviceInfoEntity::getDeviceType, deviceTypeList);
        }
        queryWrapper.ne(SubDeviceInfoEntity::getDeviceMod, UmsMod.EXIST);
        queryWrapper.isNotNull(SubDeviceInfoEntity::getGroupId);
        List<SubDeviceInfoEntity> subDeviceInfoEntityList = subDeviceMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(subDeviceInfoEntityList)) {
            log.error("查询子设备信息为空");
            return null;
        }
        List<UmsSubDeviceQueryDto> list = new ArrayList<>();
        for (SubDeviceInfoEntity subDeviceInfoEntity : subDeviceInfoEntityList) {
            UmsSubDeviceQueryDto umsSubDeviceQueryDto = new UmsSubDeviceQueryDto();
            umsSubDeviceQueryDto.setId(subDeviceInfoEntity.getId());
            umsSubDeviceQueryDto.setName(subDeviceInfoEntity.getName());
            umsSubDeviceQueryDto.setStatus(subDeviceInfoEntity.getDeviceStatus());
            umsSubDeviceQueryDto.setGroupId(subDeviceInfoEntity.getGroupId());
            umsSubDeviceQueryDto.setDeviceMod(subDeviceInfoEntity.getDeviceMod());
            umsSubDeviceQueryDto.setDeviceType(subDeviceInfoEntity.getDeviceType());
            list.add(umsSubDeviceQueryDto);
        }

        return list;
    }

    @Override
    public Boolean queryDeviceGroupNotify(String umsId) {
        log.info("请求获取所有设备分组信息入参:{}", umsId);
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(umsId);
        String sessionId = deviceInfoEntity.getSessionId();
        QueryAllDeviceGroupRequest queryDeviceRequest = new QueryAllDeviceGroupRequest();
        queryDeviceRequest.setSsid(Integer.valueOf(sessionId));
        QueryAllDeviceGroupResponse response = umsClient.getalldevgroup(queryDeviceRequest);
        log.info("请求获取所有设备分组信息应答:{}", response);
        String error = "请求获取所有设备分组信息失败:{},{},{}";
        responseUtil.handleUMSNotifyRes(error, DeviceErrorEnum.DEVICE_GROUP_NOTIFY_FAILED, response);
        listener.setListenerCallback(response.getResp().getSsid() + "_" + response.getResp().getSsno(), new NotifyCallback() {
            @Override
            public Boolean success() {
                log.info("同步统一设备分组数据成功");
                return true;
            }

            @Override
            public Boolean failure() {
                UmsNotifyQueryTask notifyQueryTask = map.get(sessionId);
                if (notifyQueryTask == null) {
                    UmsNotifyQueryTask task = new UmsNotifyQueryTask(umsId);
                    task.run();
                }
                return false;
            }
        });
        return true;
    }


}
