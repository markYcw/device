package com.kedacom.power.entity;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class DeviceConfig {
    /**
     * 设备类型
     */
    private String devType;

    /**
     * 设备子类型
     */
    private String auxDevType;

    /**
     * 设备序号
     */
    private String index;

    /**
     * 设备硬件版本号
     */
    private String devHardwareVer;

    /**
     * 设备软件版本号
     */
    private String devSoftwareVer;

    /**
     * 用户名同CH9121名
     */
    private String moduleName;

    /**
     * CH9121网络MAC地址
     */
    private String devMac;

    /**
     * CH9121 ip地址
     */
    private String devIp;

    /**
     * CH9121网关ip
     */
    private String devGatewayIp;

    /**
     * CH9121子网掩码
     */
    private String devIpMask;

    /**
     * DHCP 使能，是否启用DHCP,1:启用，0：不启用
     */
    private String dhcpEnable;

    /**
     * 预留暂未启用
     */
    private String breserved1;

    /**
     * 预留暂未启用
     */
    private String breserved2;

    /**
     * 预留暂未启用
     */
    private String breserved3;

    /**
     * 预留暂未启用
     */
    private String breserved4;

    /**
     * 预留暂未启用
     */
    private String breserved5;

    /**
     * 串口协商配置标志 1：启用 0：禁用
     */
    private String comCfgEn;

    /**
     * 预留暂未启用
     */
    private String breserved6;

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getAuxDevType() {
        return auxDevType;
    }

    public void setAuxDevType(String auxDevType) {
        this.auxDevType = auxDevType;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDevHardwareVer() {
        return devHardwareVer;
    }

    public void setDevHardwareVer(String devHardwareVer) {
        this.devHardwareVer = devHardwareVer;
    }

    public String getDevSoftwareVer() {
        return devSoftwareVer;
    }

    public void setDevSoftwareVer(String devSoftwareVer) {
        this.devSoftwareVer = devSoftwareVer;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDevMac() {
        return devMac;
    }

    public void setDevMac(String devMac) {
        this.devMac = devMac;
    }

    public String getDevIp() {
        return devIp;
    }

    public void setDevIp(String devIp) {
        this.devIp = devIp;
    }

    public String getDevGatewayIp() {
        return devGatewayIp;
    }

    public void setDevGatewayIp(String devGatewayIp) {
        this.devGatewayIp = devGatewayIp;
    }

    public String getDevIpMask() {
        return devIpMask;
    }

    public void setDevIpMask(String devIpMask) {
        this.devIpMask = devIpMask;
    }

    public String getDhcpEnable() {
        return dhcpEnable;
    }

    public void setDhcpEnable(String dhcpEnable) {
        this.dhcpEnable = dhcpEnable;
    }

    public String getBreserved1() {
        return breserved1;
    }

    public void setBreserved1(String breserved1) {
        this.breserved1 = breserved1;
    }

    public String getBreserved2() {
        return breserved2;
    }

    public void setBreserved2(String breserved2) {
        this.breserved2 = breserved2;
    }

    public String getBreserved3() {
        return breserved3;
    }

    public void setBreserved3(String breserved3) {
        this.breserved3 = breserved3;
    }

    public String getBreserved4() {
        return breserved4;
    }

    public void setBreserved4(String breserved4) {
        this.breserved4 = breserved4;
    }

    public String getBreserved5() {
        return breserved5;
    }

    public void setBreserved5(String breserved5) {
        this.breserved5 = breserved5;
    }

    public String getComCfgEn() {
        return comCfgEn;
    }

    public void setComCfgEn(String comCfgEn) {
        this.comCfgEn = comCfgEn;
    }

    public String getBreserved6() {
        return breserved6;
    }

    public void setBreserved6(String breserved6) {
        this.breserved6 = breserved6;
    }

    @Override
    public String toString() {
        return "DeviceConfig{" +
                "devType='" + devType + '\'' +
                ", auxDevType='" + auxDevType + '\'' +
                ", index='" + index + '\'' +
                ", devHardwareVer='" + devHardwareVer + '\'' +
                ", devSoftwareVer='" + devSoftwareVer + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", devMac='" + devMac + '\'' +
                ", devIp='" + devIp + '\'' +
                ", devGatewayIp='" + devGatewayIp + '\'' +
                ", devIpMask='" + devIpMask + '\'' +
                ", dhcpEnable='" + dhcpEnable + '\'' +
                ", breserved1='" + breserved1 + '\'' +
                ", breserved2='" + breserved2 + '\'' +
                ", breserved3='" + breserved3 + '\'' +
                ", breserved4='" + breserved4 + '\'' +
                ", breserved5='" + breserved5 + '\'' +
                ", comCfgEn='" + comCfgEn + '\'' +
                ", breserved6='" + breserved6 + '\'' +
                '}';
    }
}
