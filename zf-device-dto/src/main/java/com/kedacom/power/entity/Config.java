package com.kedacom.power.entity;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class Config {
    private String devIp;

    private String devIpMask;

    private String desIp;

    private Integer desPort;

    private String devGwIp;

    public String getDevGwIp() {
        return devGwIp;
    }

    public void setDevGwIp(String devGwIp) {
        this.devGwIp = devGwIp;
    }

    public String getDevIp() {
        return devIp;
    }

    public void setDevIp(String devIp) {
        this.devIp = devIp;
    }

    public String getDevIpMask() {
        return devIpMask;
    }

    public void setDevIpMask(String devIpMask) {
        this.devIpMask = devIpMask;
    }

    public String getDesIp() {
        return desIp;
    }

    public void setDesIp(String desIp) {
        this.desIp = desIp;
    }

    public Integer getDesPort() {
        return desPort;
    }

    public void setDesPort(Integer desPort) {
        this.desPort = desPort;
    }
}
