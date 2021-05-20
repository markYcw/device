package com.kedacom.core;

import cn.hutool.core.collection.ConcurrentHashSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 连接监听管理组件
 * @author van.shu
 * @create 2021/5/17 10:02
 */
@Slf4j
public class ConnectorListenerManager {

    private static volatile ConcurrentHashSet<ConnectorListener> LISTENERS = new ConcurrentHashSet<>();


    private ExecutorService executorService;


    private ConnectorListenerManager() {

    }

    private static volatile ConnectorListenerManager INSTANCE;


    public static ConnectorListenerManager getInstance() {

        if (INSTANCE == null) {
            synchronized (ConnectorListenerManager.class){
                if (INSTANCE == null) {
                    INSTANCE = new ConnectorListenerManager();
                }
            }
        }

        return INSTANCE;
    }


    /**
     * 注册监听器
     * @param listener 监听器
     */
    public void register(ConnectorListener listener) {

        if (listener == null) {
            return;
        }

        LISTENERS.add(listener);

    }


    /**
     * 取消注册
     * @param listener 监听器
     */
    public boolean unRegister(ConnectorListener listener) {

        if (listener == null) {
            return false;
        }
        return LISTENERS.remove(listener);
    }

    /**
     * 关闭资源方法
     */
    protected void close() {

    }


    /**
     * 发布连接状态
     * @param status 连接状态
     */
    protected void publish(ConnStatus status,String serverAddr) {

        if (CollectionUtils.isEmpty(LISTENERS)) {
            log.info("listeners is empty !");
            return;
        }

        synchronized (ConnectorListenerManager.class) {

            PublishTask task = new PublishTask(status, serverAddr);

            if (null == executorService || executorService.isTerminated()) {
                executorService = new ThreadPoolExecutor(1,
                        2,
                        10,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(),
                        new ConnctorListenerThreadFactory("publish-connector-event-thread-")
                );
            }

            executorService.execute(task);

            //如果发布的是已连接的通知，则可以认为接下来很长一段时间都不会有其他通知,可以关闭线程池
            if (ConnStatus.CONNECTED == status) {
                executorService.shutdown();
            }
        }


    }


    /**
     * 这里用异步发布的方式，主要是为了避免上层业务会在监听方法里处理大量业务逻辑
     * 从而导致框架内部阻塞住
     */
    static class PublishTask implements Runnable {

        private ConnStatus status;

        private String serverAddr;

        public PublishTask(ConnStatus status,String serverAddr) {

            this.status = status;

            this.serverAddr = serverAddr;

        }

        public void setStatus(ConnStatus status) {
            this.status = status;
        }

        public void setServerAddr(String serverAddr) {
            this.serverAddr = serverAddr;
        }

        @Override
        public void run() {

            try {

                for (ConnectorListener listener : LISTENERS) {
                    doPublish(listener, status);
                }
                log.info("publish the status [{}] event success !", status.toString());
            } catch (Exception e) {
                log.error("publish connector event failed , status is [{}] , e ", status.toString(), e);
            }

        }

        private void doPublish(ConnectorListener listener, ConnStatus status) {

            switch (status) {

                case DIS_CONNECT:
                    listener.onClosed(serverAddr);
                    break;

                case CONNECTING:
                    listener.onConnecting(serverAddr);
                    break;

                case CONNECTED:
                    listener.onConnected(serverAddr);
                    break;

                case FAILED_CONNECT:
                    listener.onConnectFailed(serverAddr);
                    break;

                default:
                    log.error("can not match the status , the serverAddr is {} , the status is {}", serverAddr, status);
                    break;

            }

        }
    }



    static class ConnctorListenerThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        ConnctorListenerThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            this.group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }






}
