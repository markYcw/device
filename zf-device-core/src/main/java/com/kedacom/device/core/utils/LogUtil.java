package com.kedacom.device.core.utils;

import com.kedacom.msglog.MsgLogFeign;
import com.kedacom.msglog.vo.LogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/30 18:23
 * @description 操作日志工具类
 */
@Component
@Slf4j
public class LogUtil {

    @Autowired
    private MsgLogFeign msgLogFeign;

    public void operateLog(String modelName, String operationResult, String token){
        //当有新任务到来，则插入到SynchronousQueue中，由于SynchronousQueue是同步队列，因此会在池中寻找可用线程来执行，若有可以线程则执行，
        // 若没有可用线程则创建一个线程来执行该任务；若池中线程空闲时间超过指定时间60s，则该线程会被销毁
        ExecutorService service = Executors.newCachedThreadPool();

        //向日志服务发送日志
        LogVo logVo = new LogVo();
        logVo.setModelName(modelName);
        logVo.setOperationResult(operationResult);
        logVo.setToken(token);
        try {
            CompletableFuture.runAsync(()->msgLogFeign.addLog(logVo),service);
        } catch (Exception e) {
            log.error("===========向日志服务发送日志信息失败，失败原因为：{}",e);
        }
    }

}
