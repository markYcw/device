package com.kedacom.core;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 连接监控组件
 *
 * @author van.shu
 * @create 2021/5/17 16:44
 */
@Slf4j
public class NIOConnMonitor {

    /**
     * 连接重试间隔 15s
     */
    public static final long CONNECT_RETRY_INTERVAL = 15 * 1000;

    /**
     * 心跳间隔 15S
     */
    public static final long HEART_BEAT_INTERVAL = 15 * 1000;

    private NIOConnector connector;


    public NIOConnMonitor( NIOConnector connector) {

        this.connector = connector;
    }

    private MonitorThread task;

    private static AtomicBoolean isConnected = new AtomicBoolean(false);


    /**
     * 启动连接监控组件
     */
    public synchronized void start(String serverIp, Integer serverPort) {

        if (task == null) {
            task = new MonitorThread(serverIp, serverPort);
            task.setName("Conn-Monitor");
            task.setDaemon(true);
            task.start();
        }

    }

    public boolean isConnected() {

        return isConnected.get();
    }

    /**
     * 关闭方法
     */
    public void close() {
        isConnected.set(false);
        task.interrupt();
    }


    /**
     * 连接监控线程
     */

    private class MonitorThread extends Thread {

        private String serverIp;

        private Integer serverPort;

        public MonitorThread( String serverIp, Integer serverPort) {
            this.serverIp = serverIp;
            this.serverPort = serverPort;
        }

        @Override
        public void run() {

            //开始连接
            do {
                try {
                    if (connector.connect(serverIp, serverPort)) {
                        isConnected.compareAndSet(false, true);
                    }else {
                        //连接失败，等待15s后继续重试
                        Thread.sleep(CONNECT_RETRY_INTERVAL);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    //连接失败，等待15s后继续重试
                    try {
                        Thread.sleep(CONNECT_RETRY_INTERVAL);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            } while (!isConnected.get());

            //定时发送心跳请求

        }


    }


}
