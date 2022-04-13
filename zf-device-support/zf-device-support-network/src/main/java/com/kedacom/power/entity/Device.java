package com.kedacom.power.entity;

/**
 * @author gaoteng
 * @version v1.0
 * @date 2021/8/12 14:45
 * @description
 */
public class Device {

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * mac地址
     */
    private String macAddr;

    /**
     * ip地址
     */
    private String ipAddr;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceName='" + deviceName + '\'' +
                ", macAddr='" + macAddr + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                '}';
    }
}
