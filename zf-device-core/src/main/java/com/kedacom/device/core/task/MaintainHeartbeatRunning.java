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
     * 缓存登录的终端信息
     */
    public static Set<Integer> synHashSet = MtServiceImpl.synHashSet;

    @Override
    public void run() {

        // 定时维护终端心跳
        running();
    }

    public void running() {

        // 只加载一次查询
        if (!FLAG) {
            // 查询mtId不为空的终端id
            List<Integer> integers = mtService.queryMtIds();
            log.info("查询mtId不为空的终端id集合 : {}", integers);
            synHashSet.addAll(integers);
            FLAG = true;
        }
        if (CollectionUtil.isEmpty(synHashSet)) {
            log.info("查询登录的终端id集合为空");
            return;
        }
        log.info("终端登录的缓存集合synHashSet : {}", synHashSet);
        Set<Integer> invalidSet = new HashSet<>();
        for (Integer integer : synHashSet) {
            try {
                log.info("终端id : {} 发送心跳", integer);
                mtService.heartBeat(integer);
            } catch (RuntimeException e) {
                invalidSet.add(integer);
                mtService.setNullOfMtId(integer);
                mtService.logOutById(integer);
                log.error("终端id : {} 发送心跳异常, 异常信息 : {}", integer, e.getMessage());
            }
        }
        synHashSet.removeAll(invalidSet);

    }

}
