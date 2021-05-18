package com.kedacom.core.handler;

import com.kedacom.core.pojo.Response;
import com.kedacom.exception.KMException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author van.shu
 * @create 2021/5/13 19:28
 */
@Slf4j
public class ProcessRequests {

    private static final Map<Integer, RequestFutureInfo> FUTURE_INFO_MAP = new ConcurrentHashMap<>();

    public ProcessRequests() {
        CleanThread clean = new CleanThread();
        clean.setName("Clean-UnFinish-Request-Thread");
        clean.setDaemon(true);
        clean.start();
        log.info("Start Clean-UnFinish-Request-Thread !");
    }

    public void putFuture(Integer requestId, CompletableFuture<Response> future,Class<?> clazz) {
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

        if (null!= futureInfo && null != futureInfo.getFuture()) {
            futureInfo.getFuture().complete(response);
        } else {
            throw new IllegalStateException();
        }
    }

    public void exception(KMException ex,Integer requestId) {
        RequestFutureInfo futureInfo = FUTURE_INFO_MAP.remove(requestId);

        if (null!= futureInfo && null != futureInfo.getFuture()) {
            futureInfo.getFuture().completeExceptionally(ex);
        } else {
            throw new IllegalStateException();
        }
    }


    private static class CleanThread extends Thread{

        @Override
        public void run() {
            clean();
        }

        private void clean() {
            
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
