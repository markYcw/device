package com.kedacom.core.handler;

import com.kedacom.core.pojo.Response;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author van.shu
 * @create 2021/5/13 19:28
 */
public class ProcessRequests {

    private static final Map<Integer, CompletableFuture<Response>> UNPROCESSED_RESPONSE_FUTURES = new ConcurrentHashMap<>();

    public void put(Integer requestId, CompletableFuture<Response> future) {
        UNPROCESSED_RESPONSE_FUTURES.put(requestId, future);
    }

    public void complete(Response response) {

        CompletableFuture<Response> future = UNPROCESSED_RESPONSE_FUTURES.remove(response.acquireSsno());

        if (null != future) {
            future.complete(response);
        } else {
            throw new IllegalStateException();
        }
    }


}
