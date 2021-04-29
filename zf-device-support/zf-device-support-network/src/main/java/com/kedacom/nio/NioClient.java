package com.kedacom.nio;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * NIO客户端
 * @author van.shu
 * @create 2021/4/23 14:27
 */
@Slf4j
public class NioClient {

    /**
     * 通道
     */
    private SocketChannel socketChannel;


    /**
     * 选择器
     */
    private Selector selector;

    /**
     * 连接状态
     */
    private volatile ChannelStatus status;


    public NioClient(String serverIp, int serverPort) {

        init(new InetSocketAddress(serverIp, serverPort));

    }

    /**
     * 初始化网络连接资源
     */
    private void init(InetSocketAddress address) {

        log.debug("init socket ...");

        try {

            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            //连接server端
            socketChannel.connect(address);

            selector = Selector.open();


            //注册channel
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            //设置为连接中状态
            status = ChannelStatus.CONNECTING;


        } catch (IOException e) {

            log.error("init socket error :", e);

            //设置为未连接状态
            status = ChannelStatus.UN_CONNECT;

        }

    }


    /**
     * 监听服务端
     */
    private void listen() {
        while (true){

            try {

                //这个方法会返回你感兴趣的事件的类型已经准备好的通道总数量
                int readyChannels = selector.select(1000);
                log.debug("readyChannels num:[{}]", readyChannels);
                if (readyChannels <= 0) {
                    Thread.sleep(300);
                    continue;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                continue;
            }

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

            while (keyIterator.hasNext()) {

                SelectionKey selectionKey = keyIterator.next();

                keyIterator.remove();

                //连接事件
                if (selectionKey.isConnectable()) {

                    socketChannel = (SocketChannel) selectionKey.channel();

                    //处理连接事件
                    connectionHandler(socketChannel);

                    //读事件
                } else if (selectionKey.isReadable()) {

                    socketChannel = (SocketChannel) selectionKey.channel();

                    readHandler(socketChannel);

                    //写事件
                } else if (selectionKey.isWritable()) {

                    socketChannel = (SocketChannel) selectionKey.channel();

                    writeHandler(socketChannel);

                    //无效事件
                }else {

                    //TODO


                }
            }

        }

    }


    private void connectionHandler(SocketChannel socketChannel) {

        //处理连接事件

        if (socketChannel.isConnectionPending()) {
            try {
                socketChannel.finishConnect();
                //设置为已连接状态
                status = ChannelStatus.CONNECTED;
                socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            } catch (IOException e) {

                e.printStackTrace();
                //这是未连接
                status = ChannelStatus.UN_CONNECT;
            }

        }

    }

    private void readHandler(SocketChannel socketChannel) {

        //TODO 读取从server端来的数据

        //socketChannel.read();

    }

    private void writeHandler(SocketChannel socketChannel) {

        //TODO 写数据到server端
       // socketChannel.write();

    }

    /**
     * 状态自己内部维护，不要暴露出去
     * @param status 连接状态
     */
    private void setStatus(ChannelStatus status) {
        this.status = status;
    }


    public ChannelStatus getStatus() {
        return this.status;
    }

}
