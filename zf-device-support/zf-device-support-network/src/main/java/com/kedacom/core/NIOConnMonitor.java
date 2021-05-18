package com.kedacom.core;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * 连接监控组件
 *
 * @author van.shu
 * @create 2021/5/17 16:44
 */
@Slf4j
public class NIOConnMonitor {

    private SocketChannel channel;

    /**
     * 初始化状态为未连接
     */
    private volatile ConnStatus status = ConnStatus.DIS_CONNECT;

    public NIOConnMonitor(SocketChannel channel) {
        this.channel = channel;
    }


    private MonitorThread task;


    /**
     * 启动连接监控组件
     */
    public void start(String serverIp, Integer serverPort) {

        if (task == null) {
            task = new MonitorThread(channel, serverIp, serverPort);
            task.setName("Conn-Monitor");
            task.setDaemon(true);
            task.start();
        }

    }

    public void close() {

    }


    /**
     * 连接监控线程
     */

    private  class MonitorThread extends Thread {

        private SocketChannel channel;

        private String serverIp;

        private Integer serverPort;

        public MonitorThread(SocketChannel channel,String serverIp,Integer serverPort) {
            this.channel = channel;
            this.serverIp = serverIp;
            this.serverPort = serverPort;
        }

        @Override
        public void run() {

            //如果状态是未连接，则开始连接
            if (status.equals(ConnStatus.DIS_CONNECT)) {
                try {
                    connect(channel, serverIp, serverPort);
                } catch (IOException ignored) {
                    //ignored ex
                }
            }

            //定时发送心跳请求


        }

        public void connect(SocketChannel socketChannel, String serverIp, int serverPort) throws IOException {

            do {
                try {
                    socketChannel.connect(new InetSocketAddress(InetAddress.getByName(serverIp), serverPort));
                    status = ConnStatus.CONNECTED;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (status.equals(ConnStatus.DIS_CONNECT));

            log.info("客户端信息：" + socketChannel.getLocalAddress().toString() + ":" + socketChannel.socket().getLocalPort());
            log.info("服务器信息：" + socketChannel.getRemoteAddress().toString() + ":" + socketChannel.socket().getPort());
        }
    }


}
