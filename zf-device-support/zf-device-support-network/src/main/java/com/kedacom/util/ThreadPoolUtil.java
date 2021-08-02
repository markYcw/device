package com.kedacom.util;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ThreadPoolUtil
 * @Description: 线程池
 * @author: hxj
 * @date: 2021/5/24  下午 6:36
 * @Version: 1.0
 */
public class ThreadPoolUtil {

    private static volatile ThreadPoolExecutor instance;

    private final static Object lock = new Object();

    public static ThreadPoolExecutor getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ThreadPoolExecutor(2, 10, 60L, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(100), new CustomizableThreadFactory("SyncDeviceData-"));
                }
            }
        }
        return instance;
    }
}
