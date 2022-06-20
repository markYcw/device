package com.kedacom.power.util;

import com.kedacom.power.common.RequestType;
import com.kedacom.power.entity.Device;
import com.kedacom.power.entity.DeviceConfig;
import com.kedacom.power.entity.DevicePortConfig;
import com.kedacom.power.entity.NetDeviceConfig;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class DataParser {

    public Object parseData(ByteBuffer buffer) {
        Object result = new Object();
        if (null == buffer) {
            return null;
        }
        buffer.clear();
        //通信标识
        StringBuilder builder = new StringBuilder();
        char c = (char) buffer.get();
        while (c != '\0' && buffer.position() < 16) {
            builder.append(c);
            c = (char) buffer.get();
        }
        if (!RequestType.COMMUNICATION_ID.equals(builder.toString())) {
            return null;
        }
        //请求头  不同的请求头解析方式不一样
        String requestType = Integer.toHexString(buffer.get() & 0xff);
        if (RequestType.SEARCH_BACK_TYPE.equals(requestType)) {
            result = parseSearchData(buffer);
        }
        if (RequestType.GET_CONFIG_BACK_TYPE.equals(requestType)) {
            result = parseConfigData(buffer);
        }
        if (RequestType.RESTORE_BACK_TYPE.equals(requestType)) {
            result = parseRestoreData(buffer);
        }
        if (RequestType.CONFIG_BACK_TYPE.equals(requestType)) {
            result = parseConfigData(buffer);
        }
        return result;
    }

    private String parseRestoreData(ByteBuffer buffer) {
        byte[] bytes = new byte[6];
        buffer.get(bytes);
        return HexUtils.getMacAddr(bytes);
    }

    /**
     * 解析搜索数据
     *
     * @param buffer
     * @return
     */
    private Device parseSearchData(ByteBuffer buffer) {
        Device device = new Device();
        byte[] fourByte = new byte[4];
        byte[] sixByte = new byte[6];
        //mac地址
        buffer.get(sixByte);
        String macAddr = HexUtils.getMacAddr(sixByte);
        device.setMacAddr(macAddr);
        //pcMac
        buffer.get(sixByte);
        //数据长度
        Integer dataLength = HexUtils.convertHexToTen(buffer.get());
        int max = buffer.position() + dataLength;
        //ip
        buffer.get(fourByte);
        String ip = HexUtils.getIp(fourByte);
        device.setIpAddr(ip);
        //设备名
        StringBuilder builder = new StringBuilder();
        char c = (char) buffer.get();
        while (c != ' ' && buffer.position() < max) {
            builder.append(c);
            c = (char) buffer.get();
        }
        device.setDeviceName(builder.toString());
        return device;
    }

    private NetDeviceConfig parseConfigData(ByteBuffer buffer) {
        //两个mac地址
        byte[] sixBytes = new byte[6];
        buffer.get(sixBytes);
        buffer.get(sixBytes);
        //数据长度
        buffer.get();
        //实际数据
        NetDeviceConfig netDeviceConfig = new NetDeviceConfig();
        //设备
        DeviceConfig deviceConfig = parseDeviceConfig(buffer);
        //端口 两个
        List<DevicePortConfig> list = new ArrayList<>();
        list.add(parseDevicePortConfig(buffer));
        list.add(parseDevicePortConfig(buffer));

        netDeviceConfig.setDeviceConfig(deviceConfig);
        netDeviceConfig.setDevicePortConfigs(list);
        return netDeviceConfig;
    }

    private DeviceConfig parseDeviceConfig(ByteBuffer buffer) {
        DeviceConfig deviceConfig = new DeviceConfig();
        //设备类型
        deviceConfig.setDevType(HexUtils.byteToStr(buffer.get()));
        //设备子类型
        deviceConfig.setAuxDevType(HexUtils.byteToStr(buffer.get()));
        //设备序号
        deviceConfig.setIndex(HexUtils.byteToStr(buffer.get()));
        //设备硬件版本号
        deviceConfig.setDevHardwareVer(HexUtils.byteToStr(buffer.get()));
        //设备软件版本号
        deviceConfig.setDevSoftwareVer(HexUtils.byteToStr(buffer.get()));
        //名称
        byte[] bytes21 = new byte[21];
        buffer.get(bytes21);
        String moduleName = HexUtils.parseByteArray(bytes21);
        deviceConfig.setModuleName(moduleName);
        //mac地址
        byte[] bytes6 = new byte[6];
        buffer.get(bytes6);
        String macAddr = HexUtils.getMacAddr(bytes6);
        deviceConfig.setDevMac(macAddr);
        //ip
        byte[] bytes4 = new byte[4];
        buffer.get(bytes4);
        String ip = HexUtils.getIp(bytes4);
        deviceConfig.setDevIp(ip);
        //网关ip
        buffer.get(bytes4);
        String gwIp = HexUtils.getIp(bytes4);
        deviceConfig.setDevGatewayIp(gwIp);
        //子网掩码
        buffer.get(bytes4);
        String ipMask = HexUtils.getIp(bytes4);
        deviceConfig.setDevIpMask(ipMask);
        //DHCP使能
        Integer dhcpEnable = HexUtils.convertHexToTen(buffer.get());
        deviceConfig.setDhcpEnable(dhcpEnable + "");
        //预留
        buffer.getShort();
        byte[] eightBytes = new byte[8];
        buffer.get(eightBytes);
        buffer.get();
        buffer.get(eightBytes);
        buffer.get();
        //串口协商配置标志
        Integer comCfgEn = HexUtils.convertHexToTen(buffer.get());
        deviceConfig.setComCfgEn(comCfgEn + "");
        //预留
        buffer.get(eightBytes);

        return deviceConfig;
    }

    private DevicePortConfig parseDevicePortConfig(ByteBuffer buffer) {
        DevicePortConfig devicePortConfig = new DevicePortConfig();
        //子设备序号
        devicePortConfig.setIndex(HexUtils.byteToStr(buffer.get()));
        //端口启用标志
        devicePortConfig.setPortEn(HexUtils.convertHexToTen(buffer.get()) + "");
        //网络工作模式
        devicePortConfig.setNetNode(HexUtils.convertHexToTen(buffer.get()) + "");
        //TCP 客户端模式下随机本地端口号
        devicePortConfig.setRandSportFlag(HexUtils.convertHexToTen(buffer.get()) + "");
        //本地端口号
        byte[] bytes2 = new byte[2];
        buffer.get(bytes2);
        devicePortConfig.setNetPort(HexUtils.getTwoByteInt(bytes2));
        //目的ip地址
        byte[] fourByte = new byte[4];
        buffer.get(fourByte);
        String ip = HexUtils.getIp(fourByte);
        devicePortConfig.setDesIp(ip);
        //目的端口号
        buffer.get(bytes2);
        devicePortConfig.setDesPort(HexUtils.getTwoByteInt(bytes2));
        //串口波特率
        buffer.get(fourByte);
        devicePortConfig.setBaudRate(HexUtils.getFourByteInt(fourByte));
        //串口数据位
        devicePortConfig.setDataSize(HexUtils.convertHexToTen(buffer.get()) + "");
        //串口停止位
        devicePortConfig.setStopBits(HexUtils.convertHexToTen(buffer.get()) + "");
        //串口校验位
        devicePortConfig.setParity(HexUtils.convertHexToTen(buffer.get()) + "");
        //PHY断开，Socket动作
        devicePortConfig.setPhyChangeHandle(HexUtils.convertHexToTen(buffer.get()) + "");
        //串口RX数据打包长度
        buffer.get(fourByte);
        devicePortConfig.setRxPktLength(HexUtils.getFourByteInt(fourByte));
        //串口RX数据打包转发的最大等待时间
        buffer.get(fourByte);
        devicePortConfig.setRxPktTimeout(HexUtils.getFourByteInt(fourByte));
        //预留未启用
        buffer.get();
        //串口复位操作
        devicePortConfig.setResetCtrl(HexUtils.convertHexToTen(buffer.get()) + "");
        //域名功能启用标志
        devicePortConfig.setDnsFlag(HexUtils.convertHexToTen(buffer.get()) + "");
        //TODO TCP客户端模式下，目的地址，域名
        buffer.get(new byte[20]);
        //保留
        buffer.get(new byte[14]);

        return devicePortConfig;
    }

    public static byte[] parseHeart(ByteBuffer buffer) {
        byte[] bytes = new byte[10];
        buffer.clear();
        buffer.position(4);
        buffer.get(bytes);
        return bytes;
    }

    public static String parseName(ByteBuffer buffer) {
        byte[] bytes = new byte[30];
        buffer.clear();
        buffer.position(19);
        buffer.get(bytes);
        String s = HexUtils.parseByteArray(bytes);
        return s.trim();
    }

    public static int parseTemperature(ByteBuffer buffer) {
        byte[] bytes = getBytes(buffer);
        int temp = HexUtils.getInt(bytes);
        return temp;
    }

    public static int parseVoltage(ByteBuffer buffer) {
        byte[] bytes = getBytes(buffer);
        int voltage = HexUtils.getInt(bytes);
        return voltage / 100;
    }

    public static Map<Integer, Float> getElectricity(ByteBuffer buffer) {
        buffer.clear();
        buffer.position(18);
        Map<Integer, Float> map = new HashMap<>(8);
        byte[] bytes2 = new byte[2];
        for (int i = 0; i < 8; i++) {
            buffer.get(bytes2);
            float electricity = HexUtils.getInt(bytes2);
            map.put(i + 1, electricity / 1000);
        }
        return map;
    }

    /**
     * 温度、电压和电流的数据位置固定
     *
     * @param readBuffer
     * @return
     */
    private static byte[] getBytes(ByteBuffer readBuffer) {
        readBuffer.clear();
        readBuffer.position(18);
        byte[] bytes = new byte[2];
        readBuffer.get(bytes);
        return bytes;
    }

    /**
     * 通道开关信息
     *
     * @param buffer
     * @return
     */
    public static Map<Integer, Integer> parseChannelState(ByteBuffer buffer) {
        buffer.clear();
        buffer.position(19);
        String bin = HexUtils.hexToBin(buffer.get());
        char[] states = bin.toCharArray();
        Map<Integer, Integer> map = new HashMap<>(8);
        for (int i = states.length - 1; i >= 0; i--) {
            if (states[i] == '0') {
                map.put(8 - i, 0);
            } else {
                map.put(8 - i, 1);
            }
        }
        return map;
    }
}
