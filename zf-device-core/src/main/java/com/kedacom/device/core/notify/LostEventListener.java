package com.kedacom.device.core.notify;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.device.core.constant.DeviceConstants;
import com.kedacom.device.core.convert.UmsDeviceConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.event.LostCntNty;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.service.DeviceManagerService;
import com.kedacom.device.ums.UmsClient;
import com.kedacom.device.ums.request.LoginRequest;
import com.kedacom.device.ums.response.LoginResponse;
import com.kedacom.exception.KMTimeoutException;
import com.kedacom.ums.requestdto.UmsDeviceInfoSyncRequestDto;
import com.kedacom.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/6/10 16:35
 */
@Slf4j
@Component
public class LostEventListener {

    @Resource
    private UmsClient umsClient;

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private DeviceManagerService deviceManagerService;

    @EventListener(LostCntNty.class)
    public void lostCntNty(LostCntNty lostCntNty) {
        log.info("掉线通知--->LostCntNty:{}", lostCntNty);

        try {
            LambdaQueryWrapper<DeviceInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
            List<DeviceInfoEntity> beforeLoginList = deviceMapper.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(beforeLoginList)) {
                log.info("掉线通知,设备登录:{}", beforeLoginList);
                // 只获取第一条平台信息，登录
                DeviceInfoEntity deviceInfoEntity = beforeLoginList.get(0);
                LoginRequest loginRequest = UmsDeviceConvert.INSTANCE.convertDeviceInfo(deviceInfoEntity);
                loginRequest.setDeviceType(DeviceConstants.DEVICETYPE);
                LoginResponse response = umsClient.login(loginRequest);
                deviceInfoEntity.setSessionId(String.valueOf(response.acquireSsid()));
                deviceInfoEntity.setUpdateTime(new Date());
                deviceMapper.updateById(deviceInfoEntity);
            }

            List<DeviceInfoEntity> afterLoginList = deviceMapper.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(afterLoginList)) {
                log.info("掉线通知,设备同步:{}", afterLoginList);
                // 只获取第一条平台信息，同步设备
                DeviceInfoEntity deviceInfoEntity = afterLoginList.get(0);
                UmsDeviceInfoSyncRequestDto request = new UmsDeviceInfoSyncRequestDto();
                request.setUmsId(deviceInfoEntity.getId());
                ThreadPoolUtil.getInstance().submit(new Runnable() {
                    @Override
                    public void run() {
                        deviceManagerService.syncDeviceData(request);
                    }
                });
            }

        } catch (KMTimeoutException e) {
            log.error("动态代理请求超时异常捕获:{}", e.getMessage());
        } catch (Exception e) {
            log.error("掉线通知,初始化设备、设备分组和设备名称拼音转化失败:{}", e.getMessage());
        }
    }
}
