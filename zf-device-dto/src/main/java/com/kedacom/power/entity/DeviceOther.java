package com.kedacom.power.entity;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class DeviceOther {
    private byte[] id;

    private String name;

    private String ip;

    private String mac;

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
