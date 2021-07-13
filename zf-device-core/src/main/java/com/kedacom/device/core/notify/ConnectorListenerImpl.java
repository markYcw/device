package com.kedacom.device.core.notify;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.rholder.retry.*;
import com.kedacom.core.ConnectorListener;
import com.kedacom.device.core.convert.UmsDeviceConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.service.DeviceManagerService;
import com.kedacom.device.ums.UmsClient;
import com.kedacom.device.ums.request.LoginRequest;
import com.kedacom.device.ums.response.LoginResponse;
import com.kedacom.ums.requestdto.UmsDeviceInfoSyncRequestDto;
import com.kedacom.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
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
 * @Date: 2021/5/27 09:06
 */
@Component
@Slf4j
public class ConnectorListenerImpl implements ConnectorListener {

    @Resource
    private UmsClient umsClient;

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private DeviceManagerService deviceManagerService;

    private static final AtomicInteger anInt = new AtomicInteger(0);

    static Retryer<Boolean> retryer;

    static {
        retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(aBoolean -> Objects.equals(aBoolean, false)) // 返回false时重试
                .retryIfExceptionOfType(RuntimeException.class) // 抛出RuntimeException时重试
                .withWaitStrategy(WaitStrategies.fixedWait(2000, TimeUnit.MILLISECONDS)) // 2s后重试
                .withStopStrategy(StopStrategies.stopAfterAttempt(15)) // 重试15次后停止
                .build();
    }


    @Override
    public void onConnected(String serverAddr) {
        log.info("连接事件status监听---已连接,serverAddr:{}", serverAddr);

        LambdaQueryWrapper<DeviceInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        List<DeviceInfoEntity> beforeLoginList = deviceMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(beforeLoginList)) {
            log.info("连接事件status监听---已连接,平台进行登录:{}", beforeLoginList);
            try {
                for (DeviceInfoEntity deviceInfoEntity : beforeLoginList) {
                    retryer.call(() -> {
                        LoginRequest loginRequest = UmsDeviceConvert.INSTANCE.convertDeviceInfo(deviceInfoEntity);
                        LoginResponse response = umsClient.login(loginRequest);
                        log.info("onConnected device login retry,times:{},loginRequest:{},deviceInfoEntity:{},response:{}", anInt.incrementAndGet(), loginRequest, deviceInfoEntity, response);
                        if (response.acquireErrcode() == 0) {
                            deviceInfoEntity.setSessionId(String.valueOf(response.acquireSsid()));
                            deviceInfoEntity.setUpdateTime(new Date());
                            deviceMapper.updateById(deviceInfoEntity);

                            log.info("连接事件status监听---已连接,平台登录成功:{},设备同步", deviceInfoEntity);
                            UmsDeviceInfoSyncRequestDto request = new UmsDeviceInfoSyncRequestDto();
                            request.setUmsId(deviceInfoEntity.getId());
                            ThreadPoolUtil.getInstance().submit(new Runnable() {
                                @Override
                                public void run() {
                                    deviceManagerService.syncDeviceData(request);
                                }
                            });
                            anInt.set(0);
                            return true;
                        }
                        return false;
                    });
                }
            } catch (ExecutionException e) {
                log.error("onConnected device login ExecutionException,times:{},error:{}", anInt, e.getMessage());
            } catch (RetryException e) {
                log.error("onConnected device login RetryException,times:{},error:{}", anInt, e.getMessage());
            }
        }

    }

    @Override
    public void onConnectFailed(String serverAddr) {
        log.error("连接事件监听status---连接失败:{}", serverAddr);
    }

    @Override
    public void onConnecting(String serverAddr) {
        log.info("连接事件监听status---连接中:{}", serverAddr);
    }

    @Override
    public void onClosed(String serverAddr) {
        log.error("连接事件监听status---未连接/连接断开:{}", serverAddr);
    }

}
