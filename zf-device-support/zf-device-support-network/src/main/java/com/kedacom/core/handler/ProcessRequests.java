package com.kedacom.core.handler;

import com.kedacom.core.pojo.Response;
import com.kedacom.exception.KMException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author van.shu
 * @create 2021/5/13 19:28
 */
@Slf4j
public class ProcessRequests {

    /**
     * 未完成请求信息的最大保留时间，3分钟
     */
    public static final long MAX_CLEAN_TIME = 3 * 60 * 1000;


    /**
     * 清理任务的时间间隔，30秒
     */
    public static final long CLEAN_INTERVAL = 30 * 1000;


    private static final Map<Integer, RequestFutureInfo> FUTURE_INFO_MAP = new ConcurrentHashMap<>();

    /**
     * 状态
     */
    private static AtomicBoolean cleanAlive = new AtomicBoolean(false);

    private CleanThread cleanTask;

    public ProcessRequests() {
        cleanTask = new CleanThread();
        cleanTask.setName("Clean-UnFinish-Request-Thread");
        cleanTask.setDaemon(true);
        cleanAlive.compareAndSet(false, true);
        cleanTask.start();
        log.info("Start Clean-UnFinish-Request-Thread !");
    }


    public void shutdown() {
        cleanAlive.compareAndSet(true, false);
        cleanTask.interrupt();
    }

    public void putFuture(Integer requestId, CompletableFuture<Response> future, Class<?> clazz) {
        RequestFutureInfo info = RequestFutureInfo.builder()
                .requestId(requestId)
                .future(future)
                .returnType(clazz)
                .requestTime(System.currentTimeMillis())
                .build();

        FUTURE_INFO_MAP.put(requestId, info);

    }

    public Class<?> getReturnType(Integer requestId) {
        RequestFutureInfo futureInfo = FUTURE_INFO_MAP.get(requestId);
        if (futureInfo == null) {
            return null;
        }
        return futureInfo.getReturnType();
    }

    public void complete(Response response) {

        RequestFutureInfo futureInfo = FUTURE_INFO_MAP.remove(response.acquireSsno());

        if (null != futureInfo && null != futureInfo.getFuture()) {
            futureInfo.getFuture().complete(response);
        } else {
            log.error("can not find the requestInfo about ssno [{}]", response.acquireSsno());
            //ignored
            //throw new IllegalStateException();
        }
    }

    public void exception(KMException ex, Integer requestId) {

        RequestFutureInfo futureInfo = FUTURE_INFO_MAP.remove(requestId);

        if (null != futureInfo && null != futureInfo.getFuture()) {
            futureInfo.getFuture().completeExceptionally(ex);
        } else {
            log.error("can not find the requestInfo about ssno [{}]", requestId);
        }
    }


    private static class CleanThread extends Thread {

        @Override
        public void run() {

            do {
                try {
                    Thread.sleep(CLEAN_INTERVAL);
                    clean();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cleanAlive.get());
        }

        private void clean() {

            Iterator<Map.Entry<Integer, RequestFutureInfo>> iterator = FUTURE_INFO_MAP.entrySet().iterator();

            long curTime = System.currentTimeMillis();
            while (iterator.hasNext()) {
                Map.Entry<Integer, RequestFutureInfo> entry = iterator.next();

                RequestFutureInfo info = entry.getValue();

                long requestTime = info.getRequestTime();

                if (curTime - requestTime > MAX_CLEAN_TIME) {

                    iterator.remove();

                    log.info("clean the requestInfo about ssno is :{} ,info {}", info.getRequestId(), info);
                }
            }

        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    static class RequestFutureInfo {

        /**
         * 请求号 ssno
         */
        private Integer requestId;

        /**
         * 响应
         */
        private CompletableFuture<Response> future;

        /**
         * 返回类型
         */
        private Class<?> returnType;

        /**
         * 请求时间戳
         */
        private long requestTime;

    }


}
