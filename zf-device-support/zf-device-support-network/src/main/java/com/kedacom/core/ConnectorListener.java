package com.kedacom.core;

/**
 * 连接监听器
 * Tips:
 * 这里需要说明一下，统一平台和监控平台不一样
 * 统一平台是我们抽象出来的概念，不像监控平台是有真实的SDK，
 * 所以监控平台是有状态可言和可反馈的
 * 但是统一平台的可用状态目前只能从TCP链路的可用性来判断
 * 所以，即使你登录了多个统一平台，拥有多个可用的ssid，那么这些
 * ssid的状态也是统一通过TCP链路的状态来反馈的，
 * 目前无法单独反馈某一个ssid是否可用
 * @author van.shu
 * @create 2021/5/17 9:50
 * @see ConnStatus
 */
public interface ConnectorListener {


    /**
     * 连接成功通知
     * @param serverAddr 服务端信息
     * @see ConnStatus#CONNECTED
     */
    void onConnected(String serverAddr);


    /**
     * 连接失败通知
     * @param serverAddr 服务端信息
     * @see ConnStatus#FAILED_CONNECT
     */
    void onConnectFailed(String serverAddr);


    /**
     * 正在连接中通知
     * @see ConnStatus#CONNECTING
     */
    void onConnecting(String serverAddr);


    /**
     * 断链通知
     * @see ConnStatus#DIS_CONNECT
     */
    void onClosed(String serverAddr);



}
