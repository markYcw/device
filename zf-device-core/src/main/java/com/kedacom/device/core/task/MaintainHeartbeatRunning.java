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

    private static final int KEEP_ALIVE_TIME = 3;

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

    /**
     * 创建线程池
     */
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
            new ThreadFactoryBuilder().setNameFormat("mt-maintain-heartbeat-pool-%d").setUncaughtExceptionHandler(new MtSyncUncaughtExceptionHandler()).build());

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

        // 设置线程池核心线程数，最大线程数
        setThreadPoolParam();
        threadPoolExecutor.setCorePoolSize(corePoolSize);
        threadPoolExecutor.setMaximumPoolSize(maximumPoolSize);

        // 将MT_MAINTAIN_HEARTBEAT_PERIOD设置为true，即在终端维护心跳期间
        MtServiceImpl.MT_MAINTAIN_HEARTBEAT_PERIOD.set(true);
        for (Integer dbId : synHashSet) {
            //分发维护心跳任务
            threadPoolExecutor.execute(new MaintainTask(dbId));
        }
        // 关闭线程池
        threadPoolExecutor.shutdown();
        // 等待分发任务的完成
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
        // 恢复 WHILE_FLAG 为 true
        WHILE_FLAG = true;
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
                try {
                    mtService.logOutById(dbId);
                } catch (Exception e2) {
                    log.error("心跳失效，终端id : {} 退出登录异常, 异常信息 : {}", dbId, e2.getMessage());
                }
            }
        }

    }

    static class MtSyncUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            log.error("UncaughtException occur in thread[{}]", t.getName(), e);
        }
    }

    private void setThreadPoolParam() {

        int size = synHashSet.size();

        if (size <= 60) {
            corePoolSize = 4;
            maximumPoolSize = 6;
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

        corePoolSize = 18;
        maximumPoolSize = 20;
    }

}
