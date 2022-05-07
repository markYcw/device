package com.kedacom.power.tcp;

import com.kedacom.power.callback.Callback;
import com.kedacom.power.common.DataPack;
import com.kedacom.power.util.RequestData;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class Controller {

    /**
     * 发送心跳
     *
     * @param selector
     * @param socketChannel
     * @param ip
     * @param canWriteMap
     * @return
     * @throws IOException
     */
    public static ByteBuffer heartBurn(Selector selector, SocketChannel socketChannel, String ip, Map<String, Boolean> canWriteMap) throws IOException {
        ByteBuffer requestParam = RequestData.getRequestParam(null, 0, DataPack.heart, null);
        ByteBuffer buffer = TcpChannel.sendAndReceive(socketChannel, ip, canWriteMap, requestParam, selector);
        return buffer;
    }

    /**
     * 设备名字
     *
     * @param selector
     * @param id
     * @param socketChannel
     * @param ip
     * @param canWriteMap
     * @return
     * @throws IOException
     */
    public static ByteBuffer equipName(Selector selector, byte[] id, SocketChannel socketChannel, String ip, Map<String, Boolean> canWriteMap) throws IOException {
        ByteBuffer requestParam = RequestData.getRequestParam(id, 2, DataPack.name, null);
        ByteBuffer buffer = TcpChannel.sendAndReceive(socketChannel, ip, canWriteMap, requestParam, selector);
        return buffer;
    }

    /**
     * 温度
     *
     * @param selector
     * @param id
     * @param socketChannel
     * @param ip
     * @param canWriteMap
     * @return
     * @throws IOException
     */
    public static ByteBuffer temperature(Selector selector, byte[] id, SocketChannel socketChannel, String ip, Map<String, Boolean> canWriteMap) throws IOException {
        ByteBuffer requestParam = RequestData.getRequestParam(id, 0, DataPack.temperature, null);
        ByteBuffer buffer = TcpChannel.sendAndReceive(socketChannel, ip, canWriteMap, requestParam, selector);
        return buffer;
    }

    /**
     * 电压
     *
     * @param selector
     * @param id
     * @param socketChannel
     * @param ip
     * @param canWriteMap
     * @return
     * @throws IOException
     */
    public static ByteBuffer voltage(Selector selector, byte[] id, SocketChannel socketChannel, String ip, Map<String, Boolean> canWriteMap) throws IOException {
        ByteBuffer requestParam = RequestData.getRequestParam(id, 0, DataPack.voltage, null);
        ByteBuffer buffer = TcpChannel.sendAndReceive(socketChannel, ip, canWriteMap, requestParam, selector);
        return buffer;
    }

    /**
     * 电流
     *
     * @param selector
     * @param id
     * @param socketChannel
     * @param ip
     * @param canWriteMap
     * @return
     * @throws IOException
     */
    public static ByteBuffer electricity(Selector selector, byte[] id, SocketChannel socketChannel, String ip, Map<String, Boolean> canWriteMap) throws IOException {
        byte[] addr = DataPack.electricity;
//        addr[1] = (byte) (addr[1] + (channel - 1));
        ByteBuffer requestParam = RequestData.getRequestParam(id, 3, addr, null);
        ByteBuffer buffer = TcpChannel.sendAndReceive(socketChannel, ip, canWriteMap, requestParam, selector);
        return buffer;
    }

    /**
     * 获取通道开关
     *
     * @param selector
     * @param id
     * @param socketChannel
     * @param ip
     * @param canWriteMap
     * @return
     * @throws IOException
     */
    public static ByteBuffer channelState(Selector selector, byte[] id, SocketChannel socketChannel, String ip, Map<String, Boolean> canWriteMap) throws IOException {
        ByteBuffer requestParam = RequestData.getRequestParam(id, 0, DataPack.channels, null);
        ByteBuffer buffer = TcpChannel.sendAndReceive(socketChannel, ip, canWriteMap, requestParam, selector);
        return buffer;
    }

    /**
     * 控制单通道开关
     *
     * @param selector
     * @param id
     * @param socketChannel
     * @param ip
     * @param canWriteMap
     * @param channel
     * @param flag
     * @param callback
     * @return
     * @throws IOException
     */
    public static ByteBuffer turn(Selector selector, byte[] id, SocketChannel socketChannel, String ip, Map<String, Boolean> canWriteMap, int channel, boolean flag, Callback callback) throws IOException {
        byte[] param = new byte[2];
        param[0] = (byte) channel;
        param[1] = flag ? (byte) 1 : (byte) 0;
        ByteBuffer requestParam = RequestData.getRequestParam(id, 1, DataPack.onOrOff, param);
        ByteBuffer buffer = TcpChannel.sendAndReceive(socketChannel, ip, canWriteMap, requestParam, selector);
        if (null != buffer) {
            List<Integer> channels = new ArrayList<>(channel);
            callback.turnon(channels);
        }
        return buffer;
    }

    /**
     * 多通道开关控制
     *
     * @param selector
     * @param id
     * @param socketChannel
     * @param ip
     * @param canWriteMap
     * @param map
     * @param callback
     * @return
     * @throws IOException
     */
    public static ByteBuffer turnMany(Selector selector, byte[] id, SocketChannel socketChannel, String ip, Map<String, Boolean> canWriteMap, Map<Integer, Integer> map, Callback callback, Map<Integer, Integer> stateMap) throws IOException {
        byte b = RequestData.channelStateByte(map);
        byte[] bytes = new byte[2];
        bytes[1] = b;
        ByteBuffer requestParam = RequestData.getRequestParam(id, 1, DataPack.channels, bytes);
        ByteBuffer byteBuffer = TcpChannel.sendAndReceive(socketChannel, ip, canWriteMap, requestParam, selector);
        if (null != byteBuffer) {
            List<Integer> onList = new ArrayList<>();
            List<Integer> offList = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : stateMap.entrySet()) {
                if (entry.getValue() == 0) {
                    offList.add(entry.getKey());
                } else {
                    onList.add(entry.getKey());
                }
            }
            if (onList.size() > 0) {
                callback.turnon(onList);
            }
            if (offList.size() > 0) {
                callback.turnoff(offList);
            }
        }
        return byteBuffer;
    }
}
