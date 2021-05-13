package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kedacom.BasePage;
import com.kedacom.acl.network.ums.requestvo.LoginPlatformRequestVo;
import com.kedacom.acl.network.ums.responsevo.DeviceInfoVo;
import com.kedacom.acl.network.ums.responsevo.UmsAlarmTypeQueryResponseVo;
import com.kedacom.acl.network.unite.UmsManagerInterface;
import com.kedacom.device.core.constant.UmsMod;
import com.kedacom.device.core.convert.UmsAlarmTypeConvert;
import com.kedacom.device.core.convert.UmsDeviceConvert;
import com.kedacom.device.core.convert.UmsGroupConvert;
import com.kedacom.device.core.exception.UmsManagerException;
import com.kedacom.device.core.mapper.AlarmTypeMapper;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.mapper.GroupMapper;
import com.kedacom.device.core.mapper.SubDeviceMapper;
import com.kedacom.device.core.service.UmsManagerService;
import com.kedacom.device.core.utils.PinYinUtils;
import com.kedacom.ums.entity.AlarmTypeEntity;
import com.kedacom.ums.entity.DeviceInfoEntity;
import com.kedacom.ums.entity.GroupInfoEntity;
import com.kedacom.ums.entity.SubDeviceInfoEntity;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
@Slf4j
@Service
public class UmsManagerServiceImpl implements UmsManagerService {

    @Resource
    GroupMapper groupMapper;

    @Resource
    DeviceMapper deviceMapper;

    @Resource
    SubDeviceMapper subDeviceMapper;

    @Resource
    AlarmTypeMapper alarmTypeMapper;

    @Resource
    UmsManagerInterface umsManagerInterface;

    @Override
    public Boolean insertUmsDevice(UmsDeviceInfoAddRequestDto requestDto) {

        log.info("新增统一平台信息参数 ： requestDto {}", requestDto);
        LoginPlatformRequestVo loginPlatformRequestVo = UmsDeviceConvert.INSTANCE.convertUmsDeviceInfoAddRequestVo(requestDto);
        //1、调中间件登录接口，成功返回sessionId
        Integer sessionId = umsManagerInterface.login(loginPlatformRequestVo);
        //TODO 先标志一下异常处理，稍后统一处理
        if (sessionId < 0) {
            log.error("登录统一平台异常");
            throw new UmsManagerException("登录统一平台异常");
        }
        //2、调中间件登录成功，将统一平台信息添加到本地
        DeviceInfoEntity deviceInfoEntity = UmsDeviceConvert.INSTANCE.convertDeviceInfoEntityForAdd(requestDto);
        //3、将返回的sessionId存入本地
        deviceInfoEntity.setSessionId(String.valueOf(sessionId));

        return deviceMapper.insert(deviceInfoEntity) > 0;
    }

    @Override
    public Boolean updateUmsDevice(UmsDeviceInfoUpdateRequestDto requestDto) {

        log.info("更新统一平台信息参数 ： requestDto {}", requestDto);
        String id = requestDto.getId();
        String deviceIp = requestDto.getDeviceIp();
        Integer devicePort = requestDto.getDevicePort();
        DeviceInfoEntity deviceInfo = deviceMapper.selectById(id);
        DeviceInfoEntity deviceInfoEntity = UmsDeviceConvert.INSTANCE.convertDeviceInfoEntityForUpdate(requestDto);
        //deviceIp和devicePort如果有变化，需要进行中间件的登出，登录
        if (!deviceInfo.getDeviceIp().equals(deviceIp) || !deviceInfo.getDevicePort().equals(devicePort)) {
            //1、调用中间件登出接口, 传入对应中间件的sessionId
            String sessionId = deviceInfo.getSessionId();
            Integer code = umsManagerInterface.logout(sessionId);
            if (code != 0) {
                log.error("注销统一平台异常 - umsId : [{}]", id);
                throw new UmsManagerException("注销统一平台异常");
            }
            //2、调用中间件登录接口, 成功后将获取的sessionId存入本地
            LoginPlatformRequestVo loginPlatformRequestVo = UmsDeviceConvert.INSTANCE.convertUmsDeviceInfoUpdateRequestVo(requestDto);
            Integer newSessionId = umsManagerInterface.login(loginPlatformRequestVo);
            if (newSessionId < 0) {
                log.error("登录统一平台异常");
                throw new UmsManagerException("登录统一平台异常");
            }
            deviceInfoEntity.setSessionId(String.valueOf(newSessionId));

        }

        return deviceMapper.updateById(deviceInfoEntity) > 0;
    }

    @Override
    public Boolean isRepeatForName(String id, String name) {

        LambdaQueryWrapper<DeviceInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceInfoEntity::getName, name);
        if (id != null) {
            wrapper.eq(DeviceInfoEntity::getId, id);
        }
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectOne(wrapper);

        return deviceInfoEntity != null;
    }

    @Override
    public Boolean isRepeatForDeviceIp(String id, String deviceIp) {

        LambdaQueryWrapper<DeviceInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceInfoEntity::getDeviceIp, deviceIp);
        if (id != null) {
            wrapper.eq(DeviceInfoEntity::getId, id);
        }
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectOne(wrapper);

        return deviceInfoEntity != null;
    }

    @Override
    public Boolean isRepeatForDevicePort(String id, Integer devicePort) {

        LambdaQueryWrapper<DeviceInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceInfoEntity::getDevicePort, devicePort);
        if (id != null) {
            wrapper.eq(DeviceInfoEntity::getId, id);
        }
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectOne(wrapper);

        return deviceInfoEntity != null;
    }

    @Override
    public Boolean deleteUmsDevice(UmsDeviceInfoDeleteRequestDto requestDto) {

        String umsId = requestDto.getUmsId();
        log.info("删除统一平台信息参数 ： requestDto [{}]", umsId);
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(umsId);
        if (deviceInfoEntity == null) {
            log.error("删除的统一平台信息不存在");
            return false;
        }
        String sessionId = deviceInfoEntity.getSessionId();
        Integer code = umsManagerInterface.logout(sessionId);
        if (code != 0) {
            log.error("注销统一平台异常 - umsId : [{}]", deviceInfoEntity.getId());
            throw new UmsManagerException("注销统一平台异常");
        }
        //调用中间件登出接口成功，删除本地统一平台信息
        deviceMapper.deleteById(umsId);
        //删除统一平台下挂载的子设备
        LambdaQueryWrapper<SubDeviceInfoEntity> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(SubDeviceInfoEntity::getUmsId, umsId);
        subDeviceMapper.delete(deleteWrapper);

        return true;
    }

    @Override
    public Boolean notifyThirdServiceSyncData(UmsDeviceInfoSyncRequestDto requestDto) {

        String umsId = requestDto.getUmsId();
        log.info("通知第三方服务同步数据：{}", umsId);
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(umsId);
        if (deviceInfoEntity == null) {
            log.error("查询统一平台信息为空 - umsId : [{}]", umsId);
            return false;
        }
        String sessionId = deviceInfoEntity.getSessionId();
        //调用接口，成功即返回信息
        Boolean aBoolean = umsManagerInterface.notifyThirdServiceSyncData(sessionId);
        if (!aBoolean) {
            log.error("通知第三方服务同步设备列表失败");
            throw new UmsManagerException("通知第三方服务同步设备列表失败");
        }

        return true;
    }

    @Override
    public Boolean syncDeviceData(UmsDeviceInfoSyncRequestDto requestDto) {

        String umsId = requestDto.getUmsId();
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(umsId);
        if (deviceInfoEntity == null) {
            log.error("查询统一平台信息为空 - umsId : [{}]", umsId);
            return false;
        }
        String sessionId = deviceInfoEntity.getSessionId();
        DeviceInfoVo deviceInfoVo = umsManagerInterface.syncDeviceData(sessionId);
        if (deviceInfoVo == null) {
            log.error("手动同步统一平台信息失败");
            throw new UmsManagerException("手动同步统一平台信息失败");
        }
        deviceInfoEntity.setDeviceType(deviceInfoVo.getDevtype());
        deviceInfoEntity.setDeviceIp(deviceInfoVo.getDevplatip());
        deviceInfoEntity.setDevicePort(deviceInfoVo.getDevplatport());
        deviceInfoEntity.setDeviceNotifyIp(deviceInfoVo.getDevnotifyaip());
        deviceInfoEntity.setMediaIp(deviceInfoVo.getMediascheduleip());
        deviceInfoEntity.setMediaPort(deviceInfoVo.getMediascheduleport());
        deviceInfoEntity.setStreamingMediaIp(deviceInfoVo.getNmediaip());
        deviceInfoEntity.setStreamingMediaPort(deviceInfoVo.getNmediaport());
        deviceInfoEntity.setStreamingMediaRecPort(deviceInfoVo.getRecport());
        deviceInfoEntity.setUpdateTime(new Date());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        deviceInfoEntity.setLastSyncThirdDeviceTime(sf.format(new Date()));

        return deviceMapper.updateById(deviceInfoEntity) > 0;
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
    public BasePage<UmsDeviceInfoSelectResponseDto> selectUmsDeviceList(UmsDeviceInfoSelectRequestDto requestDto) {

        Page<DeviceInfoEntity> page = new Page<>();
        page.setCurrent(requestDto.getCurPage());
        page.setSize(requestDto.getPageSize());

        LambdaQueryWrapper<DeviceInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        String deviceName = requestDto.getName();
        if (StrUtil.isNotBlank(deviceName)) {
            queryWrapper.like(DeviceInfoEntity::getName, requestDto.getName());
        }
        String deviceType = requestDto.getDeviceType();
        if (StrUtil.isNotBlank(deviceType)) {
            queryWrapper.eq(DeviceInfoEntity::getDeviceType, deviceType);
        }
        queryWrapper.orderByDesc(DeviceInfoEntity::getUpdateTime)
                .orderByDesc(DeviceInfoEntity::getCreateTime);
        Page<DeviceInfoEntity> resultPage = deviceMapper.selectPage(page, queryWrapper);
        List<DeviceInfoEntity> records = resultPage.getRecords();
        if (CollectionUtil.isEmpty(records)) {
            log.error("统一平台信息为空");
            return null;
        }
        log.info("查询统一平台信息 ： records [{}]", records);
        List<UmsDeviceInfoSelectResponseDto> umsDeviceInfoSelectResponseDtoList = UmsDeviceConvert.INSTANCE.convertUmsDeviceInfoSelectResponseDtoList(records);

        BasePage<UmsDeviceInfoSelectResponseDto> basePage = new BasePage<>();
        basePage.setTotal(resultPage.getTotal());
        basePage.setTotalPage(resultPage.getPages());
        basePage.setCurPage(requestDto.getCurPage());
        basePage.setPageSize(requestDto.getPageSize());
        basePage.setData(umsDeviceInfoSelectResponseDtoList);

        return basePage;
    }

    @Override
    public BasePage<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceList(UmsSubDeviceInfoQueryRequestDto requestDto) {

        Page<SubDeviceInfoEntity> page = new Page<>();
        page.setCurrent(requestDto.getCurPage());
        page.setSize(requestDto.getPageSize());

        LambdaQueryWrapper<SubDeviceInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        String umsId = requestDto.getUmsId();
        if (StrUtil.isNotBlank(umsId)) {
            queryWrapper.eq(SubDeviceInfoEntity::getUmsId, umsId);
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
            queryWrapper.eq(SubDeviceInfoEntity::getStatus, status);
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
            queryWrapper.eq(SubDeviceInfoEntity::getUmsId, umsId);
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
        List<SubDeviceInfoEntity> subDeviceInfoEntities = subDeviceMapper.selectBatchIds(gbIds);
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
    public List<UmsAlarmTypeQueryResponseDto> updateUmsAlarmTypeList() {

        //调用接口，获取告警类型列表
        List<UmsAlarmTypeQueryResponseVo> umsAlarmTypeQueryResponseVoList = umsManagerInterface.updateUmsAlarmTypeList();
        if (CollectionUtil.isEmpty(umsAlarmTypeQueryResponseVoList)) {
            log.error("从远端获取告警类型列表信息失败");
            throw new UmsManagerException("从远端获取告警类型列表信息失败");
        }
        log.info("从远端获取告警类型列表信息 : [{}]", umsAlarmTypeQueryResponseVoList);
        List<AlarmTypeEntity> entityList = UmsAlarmTypeConvert.INSTANCE.convertAlarmTypeEntityList(umsAlarmTypeQueryResponseVoList);


        return null;
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
        queryWrapper.eq(GroupInfoEntity::getUmsId, umsId)
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
            queryWrapper.eq(GroupInfoEntity::getUmsId, umsId);
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
        log.info("filter count is {}",count);
        Map<String, List<UmsSubDeviceQueryDto>> listMap = deviceQueryDtoList.stream().filter(x -> !UmsMod.EXIST.equals(x.getDeviceMod())).collect(Collectors.groupingBy(UmsSubDeviceQueryDto::getGroupId, Collectors.toList()));
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
                responseDto.setDeviceFaultNum((int)deviceFaultNum);
                responseDto.setDeviceOnlineNum((int)deviceOnlineNum);
                responseDto.setDeviceOfflineNum((int)deviceOfflineNum);

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
            queryWrapper.eq(SubDeviceInfoEntity::getStatus, deviceStatus);
        }
        List<String> deviceTypeList = requestDto.getDeviceTypeList();
        if (CollectionUtil.isNotEmpty(deviceTypeList)) {
            queryWrapper.in(SubDeviceInfoEntity::getDeviceType, deviceTypeList);
        }
        queryWrapper.ne(SubDeviceInfoEntity::getGroupId, "");
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
            umsSubDeviceQueryDto.setStatus(subDeviceInfoEntity.getStatus());
            umsSubDeviceQueryDto.setGroupId(subDeviceInfoEntity.getGroupId());
            umsSubDeviceQueryDto.setDeviceMod(subDeviceInfoEntity.getDeviceMod());
            umsSubDeviceQueryDto.setDeviceType(subDeviceInfoEntity.getDeviceType());
            list.add(umsSubDeviceQueryDto);
        }

        return list;
    }

    public void dealUmsManageException() {



    }

}
