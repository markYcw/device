package com.kedacom.power.message;

import java.util.Map;

/**
 * @author gaoteng
 * @version v1.0
 * @date 2021/8/10 9:08
 * @description
 */
public class RequestParam {

    private String mac;

    private String ip;

    private int channel;

    private boolean channelStatus;

    private Map<Integer, Integer> statusMap;

    //功能码
    private byte functionMark;

    private byte[] dataAddr;

    private byte dataLength;

    private byte[] controlParam;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public boolean getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(boolean channelStatus) {
        this.channelStatus = channelStatus;
    }

    public Map<Integer, Integer> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<Integer, Integer> statusMap) {
        this.statusMap = statusMap;
    }

    public byte getFunctionMark() {
        return functionMark;
    }

    public void setFunctionMark(byte functionMark) {
        this.functionMark = functionMark;
    }

    public byte[] getDataAddr() {
        return dataAddr;
    }

    public void setDataAddr(byte[] dataAddr) {
        this.dataAddr = dataAddr;
    }

    public byte getDataLength() {
        return dataLength;
    }

    public void setDataLength(byte dataLength) {
        this.dataLength = dataLength;
    }

    public byte[] getControlParam() {
        return controlParam;
    }

    public void setControlParam(byte[] controlParam) {
        this.controlParam = controlParam;
    }
}
