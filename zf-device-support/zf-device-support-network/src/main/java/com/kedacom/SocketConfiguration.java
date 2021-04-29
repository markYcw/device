package com.kedacom;

import com.kedacom.protocol.SocketProtocol;

/**
 * @author van.shu
 * @create 2021/4/23 15:14
 */
public class SocketConfiguration {

    /**
     * 服务端连接地址，默认127.0.0.1
     */
    private String remoteHost = "127.0.0.1";


    /**
     * 服务端连接端口
     */
    private int serverPort;

    /**
     * 网络协议
     */
    private SocketProtocol socketProtocol;


    /**
     * 心跳间隔 单位 ms
     */
    private long heartBeatInterval = 5000;


}
