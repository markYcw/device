package com.kedacom.power.entity;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class DevicePortConfig {
    /**
     * 子设备序号
     */
    private String index;

    /**
     * 端口启用标志， 1：启用； 0：不启用
     */
    private String portEn;

    /**
     * 网络工作模式: 0: TCP SERVER;1: TCP CLENT; 2: UDP SERVER 3：UDP CLIENT
     */
    private String netNode;

    /**
     * TCP 客户端模式下随机本地端口号，1：随机 0: 不随机
     */
    private String randSportFlag;

    /**
     * 本地端口号
     */
    private Integer netPort;

    /**
     * 目的ip地址
     */
    private String desIp;

    /**
     * 目的端口号
     */
    private Integer desPort;

    /**
     * 串口波特率: 300---921600bps
     */
    private Integer baudRate;

    /**
     * 串口数据位: 5---8位
     */
    private String dataSize;

    /**
     * 串口停止位 ：0表示1个停止位; 1表示1.5个停止位; 2表示2个停止位
     */
    private String stopBits;

    /**
     * 串口校验位: 4表示无校验，0表示奇校验; 1表示偶校验; 2表示标志位(MARK,置1); 3表示空白位(SPACE,清0);
     */
    private String parity;

    /**
     * PHY断开，Socket动作，1：关闭Socket 0：不动作
     */
    private String phyChangeHandle;

    /**
     * 串口RX数据打包长度，最大1024
     */
    private Integer rxPktLength;

    /**
     * 串口RX数据打包转发的最大等待时间,单位为: 10ms,0则表示关闭超时功能
     */
    private Integer rxPktTimeout;

    /**
     * 预留未启用
     */
    private String resv;

    /**
     * 串口复位操作: 0表示不清空串口数据缓冲区; 1表示连接时清空串口数据缓冲区
     */
    private String resetCtrl;

    /**
     * 域名功能启用标志，1：启用 0：不启用
     */
    private String dnsFlag;

    /**
     * TCP客户端模式下，目的地址，域名
     */
    private String domainName;

    /**
     * 保留
     */
    private String breserved;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getPortEn() {
        return portEn;
    }

    public void setPortEn(String portEn) {
        this.portEn = portEn;
    }

    public String getNetNode() {
        return netNode;
    }

    public void setNetNode(String netNode) {
        this.netNode = netNode;
    }

    public String getRandSportFlag() {
        return randSportFlag;
    }

    public void setRandSportFlag(String randSportFlag) {
        this.randSportFlag = randSportFlag;
    }

    public Integer getNetPort() {
        return netPort;
    }

    public void setNetPort(Integer netPort) {
        this.netPort = netPort;
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

    public Integer getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(Integer baudRate) {
        this.baudRate = baudRate;
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }

    public String getStopBits() {
        return stopBits;
    }

    public void setStopBits(String stopBits) {
        this.stopBits = stopBits;
    }

    public String getParity() {
        return parity;
    }

    public void setParity(String parity) {
        this.parity = parity;
    }

    public String getPhyChangeHandle() {
        return phyChangeHandle;
    }

    public void setPhyChangeHandle(String phyChangeHandle) {
        this.phyChangeHandle = phyChangeHandle;
    }

    public Integer getRxPktLength() {
        return rxPktLength;
    }

    public void setRxPktLength(Integer rxPktLength) {
        this.rxPktLength = rxPktLength;
    }

    public Integer getRxPktTimeout() {
        return rxPktTimeout;
    }

    public void setRxPktTimeout(Integer rxPktTimeout) {
        this.rxPktTimeout = rxPktTimeout;
    }

    public String getResv() {
        return resv;
    }

    public void setResv(String resv) {
        this.resv = resv;
    }

    public String getResetCtrl() {
        return resetCtrl;
    }

    public void setResetCtrl(String resetCtrl) {
        this.resetCtrl = resetCtrl;
    }

    public String getDnsFlag() {
        return dnsFlag;
    }

    public void setDnsFlag(String dnsFlag) {
        this.dnsFlag = dnsFlag;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getBreserved() {
        return breserved;
    }

    public void setBreserved(String breserved) {
        this.breserved = breserved;
    }

    @Override
    public String toString() {
        return "DevicePortConfig{" +
                "index='" + index + '\'' +
                ", portEn='" + portEn + '\'' +
                ", netNode='" + netNode + '\'' +
                ", randSportFlag='" + randSportFlag + '\'' +
                ", netPort=" + netPort +
                ", desIp='" + desIp + '\'' +
                ", desPort=" + desPort +
                ", baudRate=" + baudRate +
                ", dataSize='" + dataSize + '\'' +
                ", stopBits='" + stopBits + '\'' +
                ", parity='" + parity + '\'' +
                ", phyChangeHandle='" + phyChangeHandle + '\'' +
                ", rxPktLength=" + rxPktLength +
                ", rxPktTimeout=" + rxPktTimeout +
                ", resv='" + resv + '\'' +
                ", resetCtrl='" + resetCtrl + '\'' +
                ", dnsFlag='" + dnsFlag + '\'' +
                ", domainName='" + domainName + '\'' +
                ", breserved='" + breserved + '\'' +
                '}';
    }
}
