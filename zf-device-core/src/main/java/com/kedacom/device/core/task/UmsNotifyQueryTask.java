package com.kedacom.device.core.task;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import com.kedacom.device.core.service.UmsManagerService;
import com.kedacom.device.core.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: hxj
 * @Date: 2021/5/18 20:17
 */
@Slf4j
public class UmsNotifyQueryTask implements Runnable{

    private String umsId;

    public UmsNotifyQueryTask(String umsId) {
        this.umsId = umsId;
    }
    static Retryer<Boolean> retryer;

    static {
        retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Predicates.equalTo(false)) // 返回false时重试
                .retryIfExceptionOfType(RuntimeException.class) // 抛出RuntimeException时重试
                .withWaitStrategy(WaitStrategies.fixedWait(500, TimeUnit.MILLISECONDS)) // 500ms后重试
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 重试10次后停止
                .build();
    }

    @Override
    public void run() {
        try {
            retryer.call(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                        UmsManagerService umsManagerService = SpringUtil.getBean(UmsManagerService.class);
                        return umsManagerService.queryDeviceGroupNotify(umsId);
                }
            });
        } catch (ExecutionException e) {

        } catch (RetryException e) {

        }
    }
}
