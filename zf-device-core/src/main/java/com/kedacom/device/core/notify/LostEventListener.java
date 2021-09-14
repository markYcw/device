package com.kedacom.device.core.notify;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.rholder.retry.*;
import com.kedacom.device.core.convert.UmsDeviceConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.event.LostCntNty;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.service.DeviceManagerService;
import com.kedacom.device.ums.UmsClient;
import com.kedacom.device.ums.request.LoginRequest;
import com.kedacom.device.ums.response.LoginResponse;
import com.kedacom.ums.requestdto.UmsDeviceInfoSyncRequestDto;
import com.kedacom.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    static Retryer<Boolean> retryer;

    static {
        retryer = RetryerBuilder.<Boolean>newBuilder()
                // 返回false时重试
                .retryIfResult(aBoolean -> Objects.equals(aBoolean, false))
                // 抛出RuntimeException时重试
                .retryIfExceptionOfType(RuntimeException.class)
                // 2s后重试
                .withWaitStrategy(WaitStrategies.fixedWait(2000, TimeUnit.MILLISECONDS))
                // 重试10次后停止
                .withStopStrategy(StopStrategies.stopAfterAttempt(15))
                .build();
    }

    @EventListener(LostCntNty.class)
    public void lostCntNty(LostCntNty lostCntNty) {
        log.info("掉线通知--->LostCntNty:{}", lostCntNty);
        LambdaQueryWrapper<DeviceInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        List<DeviceInfoEntity> beforeLoginList = deviceMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(beforeLoginList)) {
            log.info("掉线通知事件,平台进行登录:{}", beforeLoginList);
            try {
                for (DeviceInfoEntity deviceInfoEntity : beforeLoginList) {
                    retryer.call(() -> {
                        LoginRequest loginRequest = UmsDeviceConvert.INSTANCE.convertDeviceInfo(deviceInfoEntity);
                        LoginResponse response = umsClient.login(loginRequest);
                        log.info("LostCntNty login retry,times:{},deviceInfoEntity:{},loginRequest:{},response:{}", ATOMIC_INTEGER.incrementAndGet(), deviceInfoEntity, loginRequest, response);
                        if (response.acquireErrcode() == 0) {
                            deviceInfoEntity.setSessionId(String.valueOf(response.acquireSsid()));
                            deviceInfoEntity.setUpdateTime(new Date());
                            deviceMapper.updateById(deviceInfoEntity);

                            List<DeviceInfoEntity> afterLoginList = deviceMapper.selectList(queryWrapper);
                            if (CollectionUtil.isNotEmpty(afterLoginList)) {
                                log.info("掉线通知事件,平台登录成功:{},设备同步", beforeLoginList);
                                // 只获取第一条平台信息，同步设备
                                DeviceInfoEntity deviceInfoEntity1 = afterLoginList.get(0);
                                UmsDeviceInfoSyncRequestDto request = new UmsDeviceInfoSyncRequestDto();
                                request.setUmsId(deviceInfoEntity1.getId());
                                ThreadPoolUtil.getInstance().submit(new Runnable() {
                                    @Override
                                    public void run() {
                                        deviceManagerService.syncDeviceData(request);
                                    }
                                });
                            }
                            ATOMIC_INTEGER.set(0);
                            return true;
                        }
                        return false;
                    });
                }
            } catch (ExecutionException e) {
                log.error("LostCntNty login ExecutionException,times:{},error:{}", ATOMIC_INTEGER, e.getMessage());
            } catch (RetryException e) {
                log.error("LostCntNty login RetryException,times:{},error:{}", ATOMIC_INTEGER, e.getMessage());
            }
        }
    }
}
