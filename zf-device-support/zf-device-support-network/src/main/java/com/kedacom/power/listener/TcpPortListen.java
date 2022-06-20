package com.kedacom.power.listener;

import com.kedacom.power.ControlPower;
import com.kedacom.power.callback.Callback;
import com.kedacom.power.entity.Device;
import com.kedacom.power.entity.DeviceOther;
import com.kedacom.power.tcp.Controller;
import com.kedacom.power.util.DataParser;
import com.kedacom.power.util.HexUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class TcpPortListen extends Thread {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private Map<String, SocketChannel> map;

    private Map<String, Boolean> canWriteMap;

    private Map<String, Device> devices;

    private Map<String, DeviceOther> deviceOthers;

    private Callback callback;

    private Selector readSelector;

    public void setReadSelector(Selector readSelector) {
        this.readSelector = readSelector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public void setServerSocketChannel(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    public void setMap(Map<String, SocketChannel> map) {
        this.map = map;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setCanWriteMap(Map<String, Boolean> canWriteMap) {
        this.canWriteMap = canWriteMap;
    }

    public void setDevices(Map<String, Device> devices) {
        this.devices = devices;
    }

    public void setDeviceOthers(Map<String, DeviceOther> deviceOthers) {
        this.deviceOthers = deviceOthers;
    }

    @Override
    public void run() {
        while (ControlPower.serverState) {
            try {
                int readyChannels = selector.select(2000);
                if (readyChannels <= 0) {
                    continue;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(readSelector, SelectionKey.OP_READ);

                        InetSocketAddress remoteAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
                        String hostAddress = remoteAddress.getAddress().getHostAddress();
                        map.put(hostAddress, socketChannel);
                        canWriteMap.put(hostAddress, true);

                        iterator.remove();

                        DeviceOther deviceOther = new DeviceOther();
                        Device device = new Device();
                        //发送心跳获取唯一id、mac地址
                        ByteBuffer heartData = Controller.heartBurn(readSelector, socketChannel, hostAddress, canWriteMap);
                        byte[] bytes = DataParser.parseHeart(heartData);
                        deviceOther.setId(bytes);
                        deviceOther.setIp(hostAddress);
                        device.setIpAddr(hostAddress);
                        byte[] macByte = new byte[6];
                        System.arraycopy(bytes, 1, macByte, 0, macByte.length);
                        String macAddr = HexUtils.getMacAddr(macByte);
                        deviceOther.setMac(macAddr);
                        device.setMacAddr(macAddr);
                        //设备名字
                        ByteBuffer nameData = Controller.equipName(readSelector, bytes, socketChannel, hostAddress, canWriteMap);
                        String name = DataParser.parseName(nameData);
                        deviceOther.setName(name);
                        device.setDeviceName(name);

                        devices.put(macAddr, device);
                        deviceOthers.put(macAddr, deviceOther);

                        if (null != callback) {
                            callback.online(device);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
