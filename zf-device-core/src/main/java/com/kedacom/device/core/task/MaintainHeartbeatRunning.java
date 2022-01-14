package com.kedacom.device.core.task;

import cn.hutool.core.collection.CollectionUtil;
import com.kedacom.device.core.service.MtService;
import com.kedacom.device.core.service.impl.MtServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private static boolean FLAG = false;

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
        if (!FLAG) {
            // 查询mtId不为空的终端id
            List<Integer> integers = mtService.queryIdsByMtId();
            if (CollectionUtil.isEmpty(integers)) {
                return;
            }
            log.info("查询mtId不为空的终端id集合 : {}", integers);
            synHashSet.addAll(integers);
            FLAG = true;
        }
        if (CollectionUtil.isEmpty(synHashSet)) {
            log.info("在线终端缓存为空");
            return;
        }
        log.info("在线终端缓存集合synHashSet : {}", synHashSet);
        MtServiceImpl.MT_MAINTAIN_HEARTBEAT_ADD = true;
        for (Integer integer : synHashSet) {
            try {
                log.info("终端id : {} 发送心跳", integer);
                mtService.heartBeat(integer);
            } catch (Exception e1) {
                log.error("终端id : {} 发送心跳异常, 异常信息 : {}", integer, e1.getMessage());
                synInvalidHashSet.add(integer);
                try {
                    MtServiceImpl.MT_MAINTAIN_HEARTBEAT_REMOVE = false;
                    mtService.logOutById(integer);
                    MtServiceImpl.MT_MAINTAIN_HEARTBEAT_REMOVE = true;
                } catch (Exception e2) {
                    log.error("心跳失效，终端id : {} 退出登录异常, 异常信息 : {}", integer, e2.getMessage());
                }
            }
        }
        synHashSet.removeAll(synInvalidHashSet);
        MtServiceImpl.MT_MAINTAIN_HEARTBEAT_ADD = false;
        if (CollectionUtil.isEmpty(synTransitHashSet)) {
            log.info("在线终端中转缓存为空");
            return;
        }

        synHashSet.addAll(synTransitHashSet);
    }

}
