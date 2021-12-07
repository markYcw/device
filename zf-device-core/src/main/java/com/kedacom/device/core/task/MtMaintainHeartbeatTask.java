package com.kedacom.device.core.task;

import com.kedacom.device.core.service.MtService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/6
 */
@Component
public class MtMaintainHeartbeatTask implements CommandLineRunner {

    @Resource
    MtService mtService;

    @Override
    public void run(String... args) throws Exception {

        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(3,
                new BasicThreadFactory.Builder().namingPattern("scheduled-pool-%d").daemon(true).build());

//        scheduledExecutorService.scheduleAtFixedRate(checkRunning, 500, 2000, TimeUnit.MILLISECONDS);

    }

}
