package com.kedacom.device.core.task;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.kedacom.device.core.service.MtService;
import com.kedacom.device.core.service.impl.MtServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/7
 */
@Slf4j
@Component
public class MaintainHeartbeatRunning implements Runnable {

    @Resource
    MtService mtService;

    private static boolean LOAD = false;

    private static boolean WHILE_FLAG = true;

    private static int corePoolSize = 0;

    private static int maximumPoolSize = 0;

    private static final int keepAliveTime = 3;

    /**
     * 在线终端缓存（id）
     */
    public static Set<Integer> synHashSet = MtServiceImpl.synHashSet;

    /**
     * 无效的（中间退出，掉线或被抢占）的终端缓存
     */
    public static Set<Integer> synInvalidHashSet = MtServiceImpl.synInvalidHashSet;

    /**
     * 在线终端中转缓存（id）
     */
    public static Set<Integer> synTransitHashSet = MtServiceImpl.synTransitHashSet;

    @Override
    public void run() {

        running();
    }

    public void running() {

        log.info("---------- 定时维护终端心跳中 ----------");

        // 只加载一次查询
        if (!LOAD) {
            // 查询mtId不为空的终端id
            List<Integer> integers = mtService.queryIdsByMtId();
            if (CollectionUtil.isEmpty(integers)) {
                return;
            }
            log.info("查询mtId不为空的终端id集合 : {}", integers);
            synHashSet.addAll(integers);
            LOAD = true;
        }
        if (CollectionUtil.isEmpty(synHashSet)) {
            log.info("在线终端缓存为空");
            return;
        }
        log.info("在线终端缓存集合synHashSet : {}", synHashSet);

        // 创建线程池
        setThreadPoolParam();
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("mt-maintain-heartbeat-pool-%d").build();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), namedThreadFactory);

        // 将MT_MAINTAIN_HEARTBEAT_PERIOD设置为true，即在终端维护心跳期间
        MtServiceImpl.MT_MAINTAIN_HEARTBEAT_PERIOD.set(true);
        for (Integer dbId : synHashSet) {
            //分发维护心跳任务
            threadPoolExecutor.execute(new MaintainTask(dbId));
        }
        threadPoolExecutor.shutdown();
        // 等待分发任务完成
        while (WHILE_FLAG) {
            if (threadPoolExecutor.isTerminated()) {
                // 分发任务完成， 将MT_MAINTAIN_HEARTBEAT_PERIOD设置为true，即不在终端维护心跳期间
                MtServiceImpl.MT_MAINTAIN_HEARTBEAT_PERIOD.set(false);
                WHILE_FLAG = false;
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (CollectionUtil.isNotEmpty(synInvalidHashSet)) {
            // 将失效缓存中的终端id从在线终端缓存中删除，并清空失效缓存
            synHashSet.removeAll(synInvalidHashSet);
            synInvalidHashSet.clear();
        }
        if (CollectionUtil.isEmpty(synTransitHashSet)) {
            log.info("在线终端中转缓存为空");
            return;
        }
        // 将在线终端中转缓存中的终端id添加到在线终端缓存中，并清空在线终端中转缓存
        synHashSet.addAll(synTransitHashSet);
        synTransitHashSet.clear();

        // 恢复 WHILE_FLAG
        WHILE_FLAG = true;
    }

    class MaintainTask implements Runnable {

        private Integer dbId;

        public MaintainTask(Integer dbId) {
            this.dbId = dbId;
        }

        @Override
        public void run() {

            maintainTask();
        }

        public void maintainTask() {

            try {
                log.info("终端id : {} 发送心跳", dbId);
                mtService.heartBeat(dbId);
            } catch (Exception e1) {
                log.error("终端id : {} 发送心跳异常, 异常信息 : {}", dbId, e1.getMessage());
                synInvalidHashSet.add(dbId);
                MtServiceImpl.MT_MAINTAIN_HEARTBEAT_PERIOD_LOGOUT.set(true);
                try {
                    mtService.logOutById(dbId);
                } catch (Exception e2) {
                    log.error("心跳失效，终端id : {} 退出登录异常, 异常信息 : {}", dbId, e2.getMessage());
                }

                MtServiceImpl.MT_MAINTAIN_HEARTBEAT_PERIOD_LOGOUT.set(false);
            }
        }

    }

    private void setThreadPoolParam() {

        int size = synHashSet.size();

        if (size <= 60) {
            corePoolSize = 1;
            maximumPoolSize = 2;
            return;
        }
        if (size <= 360) {
            corePoolSize = 6;
            maximumPoolSize = 8;
            return;
        }
        if (size <= 720) {
            corePoolSize = 10;
            maximumPoolSize = 12;
            return;
        }

        corePoolSize = 16;
        maximumPoolSize = 20;
    }

}
