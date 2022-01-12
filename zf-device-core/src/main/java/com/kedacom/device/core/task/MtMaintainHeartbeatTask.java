package com.kedacom.device.core.task;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class MtMaintainHeartbeatTask implements CommandLineRunner {

    @Resource
    MaintainHeartbeatRunning maintainHeartbeatRunning;

    @Override
    public void run(String... args) {

        log.info("----- 定时维护终端心跳 -----");

        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(3, new BasicThreadFactory.Builder().namingPattern("scheduled-pool-%d").daemon(true).build());

        // 40s定时发送心跳
        scheduledExecutorService.scheduleAtFixedRate(maintainHeartbeatRunning, 3, 40, TimeUnit.SECONDS);

    }

}
