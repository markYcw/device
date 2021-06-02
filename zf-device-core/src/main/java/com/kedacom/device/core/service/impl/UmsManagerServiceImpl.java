package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kedacom.BasePage;
import com.kedacom.device.core.constant.DeviceConstants;
import com.kedacom.device.core.convert.UmsDeviceConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.entity.SubDeviceInfoEntity;
import com.kedacom.device.core.exception.UmsManagerException;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.mapper.SubDeviceMapper;
import com.kedacom.device.core.runner.UmsDeviceRunner;
import com.kedacom.device.core.service.UmsManagerService;
import com.kedacom.device.ums.UmsClient;
import com.kedacom.device.ums.request.LoginRequest;
import com.kedacom.device.ums.request.LogoutRequest;
import com.kedacom.device.ums.response.LoginResponse;
import com.kedacom.device.ums.response.LogoutResponse;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
@Slf4j
@Service
public class UmsManagerServiceImpl implements UmsManagerService {

    @Resource
    UmsClient umsClient;

    @Resource
    DeviceMapper deviceMapper;

    @Resource
    UmsDeviceRunner umsDeviceRunner;

    @Resource
    SubDeviceMapper subDeviceMapper;

//    private ExecutorService executorService = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS,
//            new LinkedBlockingQueue<Runnable>(20));

    @Override
    public String insertUmsDevice(UmsDeviceInfoAddRequestDto requestDto) {

        log.info("新增统一平台信息参数 ： requestDto {}", requestDto);
        LoginRequest loginRequest = UmsDeviceConvert.INSTANCE.convertUmsDeviceInfoAddRequestVo(requestDto);
        loginRequest.setDeviceType(DeviceConstants.DEVICETYPE);
        //1、调中间件登录接口，成功返回sessionId
        LoginResponse loginResponse = umsClient.login(loginRequest);
        //TODO 先标志一下异常处理，稍后统一处理
        if (loginResponse.acquireErrcode() != 0) {
            log.error("登录统一平台异常:{}", loginResponse);
            throw new UmsManagerException("登录统一平台异常");
        }
        //2、调中间件登录成功，将统一平台信息添加到本地
        DeviceInfoEntity deviceInfoEntity = UmsDeviceConvert.INSTANCE.convertDeviceInfoEntityForAdd(requestDto);
        //3、将返回的sessionId存入本地
        deviceInfoEntity.setSessionId(String.valueOf(loginResponse.acquireSsid()));
        deviceInfoEntity.setDeviceType("UMS");
        deviceMapper.insert(deviceInfoEntity);

        //平台信息添加成功，开启自动获取远端设备及分组信息任务
        umsDeviceRunner.syncDeviceInfo(deviceInfoEntity);

        return deviceInfoEntity.getId();
    }

    @Override
    public String updateUmsDevice(UmsDeviceInfoUpdateRequestDto requestDto) {

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
            LogoutRequest logoutRequest = new LogoutRequest();
            logoutRequest.setSsid(Integer.valueOf(sessionId));
            LogoutResponse response = umsClient.logout(logoutRequest);
            if (response.acquireErrcode() != 0) {
                log.error("注销统一平台异常 - umsId : {}", id);
                throw new UmsManagerException("注销统一平台异常");
            }
            //2、调用中间件登录接口, 成功后将获取的sessionId存入本地
            LoginRequest loginRequest = UmsDeviceConvert.INSTANCE.convertUmsDeviceInfoUpdateRequestVo(requestDto);
            loginRequest.setDeviceType(DeviceConstants.DEVICETYPE);
            LoginResponse loginResponse = umsClient.login(loginRequest);
            if (loginResponse.acquireErrcode() != 0) {
                log.error("登录统一平台异常");
                throw new UmsManagerException("登录统一平台异常");
            }
            deviceInfoEntity.setSessionId(String.valueOf(loginResponse.acquireSsid()));
            deviceInfoEntity.setDeviceType("UMS");

        }
        deviceMapper.updateById(deviceInfoEntity);

        return deviceInfoEntity.getId();
    }

    @Override
    public Boolean isRepeatForName(String id, String name) {

        LambdaQueryWrapper<DeviceInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceInfoEntity::getName, name);
        if (id != null) {
            wrapper.ne(DeviceInfoEntity::getId, id);
        }
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectOne(wrapper);

        return deviceInfoEntity != null;
    }

    @Override
    public Boolean isRepeatForDeviceIp(String id, String deviceIp) {

        LambdaQueryWrapper<DeviceInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceInfoEntity::getDeviceIp, deviceIp);
        if (id != null) {
            wrapper.ne(DeviceInfoEntity::getId, id);
        }
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectOne(wrapper);

        return deviceInfoEntity != null;
    }

    @Override
    public Boolean isRepeatForDevicePort(String id, Integer devicePort) {

        LambdaQueryWrapper<DeviceInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceInfoEntity::getDevicePort, devicePort);
        if (id != null) {
            wrapper.ne(DeviceInfoEntity::getId, id);
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
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setSsid(Integer.valueOf(sessionId));
        LogoutResponse response = umsClient.logout(logoutRequest);
        if (response.acquireErrcode() != 0) {
            log.error("注销统一平台异常 - umsId : [{}]", deviceInfoEntity.getId());
            throw new UmsManagerException("注销统一平台异常");
        }
        //调用中间件登出接口成功，删除本地统一平台信息
        deviceMapper.deleteById(umsId);
        //删除统一平台下挂载的子设备
        LambdaQueryWrapper<SubDeviceInfoEntity> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(SubDeviceInfoEntity::getParentId, umsId);
        subDeviceMapper.delete(deleteWrapper);
        //删除根据平台信息获取远端设备及分组的任务
        umsDeviceRunner.notifyUmsChange(deviceInfoEntity, DeviceConstants.DEL_UMS);

        return true;
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

}
