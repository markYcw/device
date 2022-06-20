package com.kedacom.power.util;

import com.kedacom.power.common.DataPack;
import com.kedacom.power.common.Numbers;
import com.kedacom.power.common.RequestType;
import com.kedacom.power.entity.DeviceConfig;
import com.kedacom.power.entity.DevicePortConfig;
import com.kedacom.power.entity.NetDeviceConfig;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 * @Description 发送请求数据包
 */
public class RequestData {

    public static ByteBuffer getRequestData(String communicationId, Integer requestType, String macAddr, String pMacAddr, NetDeviceConfig netDeviceConfig) {
        ByteBuffer buffer = ByteBuffer.allocate(Numbers.CONSTANT_285);
        if (null != communicationId && !"".equals(communicationId)) {
            byte[] idBytes = communicationId.getBytes();
            buffer.put(idBytes);
        }
        if (buffer.position() < 16) {
            buffer.put((byte) '\0');
        }
        if (null != requestType) {
            if (requestType.equals(RequestType.CONFIG_TYPE)) {
                buffer.put((byte) 0x01);
            }
            if (requestType.equals(RequestType.GET_CONFIG_TYPE)) {
                buffer.put((byte) 0x02);
            }
            if (requestType.equals(RequestType.RESTORE_TYPE)) {
                buffer.put((byte) 0x03);
            }
            if (requestType.equals(RequestType.SEARCH_TYPE)) {
                buffer.put((byte) 0x04);
            }
        }
        if (null != macAddr && !"".equals(macAddr)) {
            String addrStr = macAddr.replaceAll("-", "");
            byte[] hexBytes = HexUtils.getHexBytes(addrStr);
            buffer.put(hexBytes);
        }
        if (null != pMacAddr && !"".equals(pMacAddr)) {
            String pAddr = pMacAddr.replaceAll("-", "");
            byte[] hexBytes = HexUtils.getHexBytes(pAddr);
            buffer.put(hexBytes);
        } else {
            buffer.put(new byte[6]);
        }
        buffer.put((byte) 100);
        if (null != netDeviceConfig) {
            DeviceConfig deviceConfig = netDeviceConfig.getDeviceConfig();
            device(buffer, deviceConfig);
            List<DevicePortConfig> list = netDeviceConfig.getDevicePortConfigs();
            devicePort(list.get(0), buffer);
            devicePort(list.get(1), buffer);
        }
        return buffer;
    }

    private static void device(ByteBuffer buffer, DeviceConfig deviceConfig) {
        buffer.put(HexUtils.stringToByte(deviceConfig.getDevType()));
        buffer.put(HexUtils.stringToByte(deviceConfig.getAuxDevType()));
        buffer.put(HexUtils.stringToByte(deviceConfig.getIndex()));
        buffer.put(HexUtils.stringToByte(deviceConfig.getDevHardwareVer()));
        buffer.put(HexUtils.stringToByte(deviceConfig.getDevSoftwareVer()));
        byte[] bytes21 = new byte[21];
        byte[] bytes = deviceConfig.getModuleName().getBytes();
        System.arraycopy(bytes, 0, bytes21, 0, bytes.length);
        buffer.put(bytes21);
        byte[] mac = HexUtils.macToByte(deviceConfig.getDevMac());
        buffer.put(mac);
        buffer.put(HexUtils.ipToByte(deviceConfig.getDevIp()));
        buffer.put(HexUtils.ipToByte(deviceConfig.getDevGatewayIp()));
        buffer.put(HexUtils.ipToByte(deviceConfig.getDevIpMask()));
        buffer.put((byte)Integer.parseInt(deviceConfig.getDhcpEnable(), 16));
        buffer.put(new byte[2]);
        byte[] bytes8 = new byte[8];
        buffer.put(bytes8);
        buffer.put((byte) '\0');
        buffer.put(bytes8);
        buffer.put((byte) '\0');
        buffer.put((byte)Integer.parseInt(deviceConfig.getComCfgEn()));
        buffer.put(bytes8);
    }

    private static void devicePort(DevicePortConfig portConfig, ByteBuffer buffer) {
        buffer.put(HexUtils.stringToByte(portConfig.getIndex()));
        buffer.put((byte)Integer.parseInt(portConfig.getPortEn(), 16));
        buffer.put((byte)Integer.parseInt(portConfig.getNetNode(), 16));
        buffer.put((byte)Integer.parseInt(portConfig.getRandSportFlag(), 16));
        buffer.put(HexUtils.intToTwoByte(portConfig.getNetPort()));
        buffer.put(HexUtils.ipToByte(portConfig.getDesIp()));
        buffer.put(HexUtils.intToTwoByte(portConfig.getDesPort()));
        buffer.put(HexUtils.intToByte(portConfig.getBaudRate()));
        buffer.put((byte)Integer.parseInt(portConfig.getDataSize(), 16));
        buffer.put((byte)Integer.parseInt(portConfig.getStopBits(), 16));
        buffer.put((byte)Integer.parseInt(portConfig.getParity(), 16));
        buffer.put((byte)Integer.parseInt(portConfig.getPhyChangeHandle(), 16));
        buffer.put(HexUtils.intToByte(portConfig.getRxPktLength()));
        buffer.put(HexUtils.intToByte(portConfig.getRxPktTimeout()));
        buffer.put((byte) '\0');
        buffer.put((byte)Integer.parseInt(portConfig.getResetCtrl(), 16));
        buffer.put((byte)Integer.parseInt(portConfig.getDnsFlag(), 16));
        byte[] bytes20 = new byte[20];
        buffer.put(bytes20);
        buffer.put(new byte[14]);
    }

    public static ByteBuffer getRequestParam(byte[] id, int type, byte[] dataAddr, byte[] controlParam) {
        ByteBuffer byteBuffer;
        if (type == 0 || type == 2 || type == 3) {
            byteBuffer = ByteBuffer.allocate(Numbers.CONSTANT_25);
        } else if (type == 1) {
            byteBuffer = ByteBuffer.allocate(Numbers.CONSTANT_27);
        }  else {
            byteBuffer = ByteBuffer.allocate(Numbers.CONSTANT_285);
        }
        byteBuffer.clear();
        //包头
        byteBuffer.put((byte) 0x02);
        byteBuffer.put((byte) 0x02);
        byteBuffer.put((byte) 0x00);
        byteBuffer.put((byte) 0x00);
        //唯一ID
        if (null != id) {
            byteBuffer.put(id);
        } else {
            byteBuffer.put(new byte[10]);
        }
        //功能码
        if (type != 1) {
            //获取
            byteBuffer.put((byte) 0x03);
        }
        if (type == 1) {
            //配置
            byteBuffer.put((byte) 0x06);
        }
        //数据地址
        byteBuffer.put(dataAddr);
        //数据长度
        if (type == 2) {
            byteBuffer.put((byte) 0xBC);
        } else if (type == 3){
            byteBuffer.put((byte) 0x08);
        } else {
            byteBuffer.put((byte) 0x01);
        }
        //控制参数
        if (null != controlParam) {
            byteBuffer.put(controlParam);
        }
        //校验码
        int position = byteBuffer.position();
        byteBuffer.put((byte) 0x00);
        //token
        byteBuffer.put(DataPack.token);
        //包尾
        byteBuffer.put((byte) 0x11);
        byteBuffer.put((byte) 0x00);
        byteBuffer.put((byte) 0x0D);
        byteBuffer.put((byte) 0x0A);

        //设置校验码
        checkSum(byteBuffer, position);
        byteBuffer.clear();

        return byteBuffer;
    }

    private static void checkSum(ByteBuffer buffer, int position) {
        byte[] array = buffer.array();
        int sum = 0;
        for (int i = 4; i < array.length - 6; i++) {
            sum += array[i];
        }
        buffer.position(position);
        buffer.put((byte) (sum & 0xff));
    }

    public static byte channelStateByte(Map<Integer, Integer> map) {
        char[] chars = new char[8];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = '0';
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            chars[8 - entry.getKey()] = entry.getValue().toString().charAt(0);
        }
        return HexUtils.binToHex(new String(chars));
    }
}
