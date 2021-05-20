package com.kedacom.core;

/**
 * 连接状态
 * @author van.shu
 * @create 2021/5/17 17:12
 */
public enum ConnStatus {

    /**
     * 未连接/连接断开
     */
    DIS_CONNECT,

    /**
     * 连接中
     */
    CONNECTING,

    /**
     * 已连接
     */
    CONNECTED,

    /**
     * 连接失败
     */
    FAILED_CONNECT,
    ;
}
