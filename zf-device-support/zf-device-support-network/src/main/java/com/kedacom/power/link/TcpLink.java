package com.kedacom.power.link;

import com.kedacom.power.ControlPower;
import com.kedacom.power.callback.Callback;
import com.kedacom.power.entity.Device;
import com.kedacom.power.entity.DeviceInfo;
import com.kedacom.power.message.*;
import lombok.extern.slf4j.Slf4j;

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
 * @author gaoteng
 * @version v1.0
 * @date 2021/8/5 15:32
 * @description
 */
@Slf4j
public class TcpLink extends Thread {

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private Map<String, SocketChannel> channelMap;

    private Map<String, DeviceInfo> deviceMap;

    private Callback callback;

    private ByteBuffer buffer = ByteBuffer.allocate(285);

    private MessageProcessor processor = MessageProcessor.getInstance();

    private MessageSender sender = MessageSender.getInstance();

    public void init(ServerSocketChannel serverSocketChannel,
                     Selector selector,
                     Map<String, DeviceInfo> deviceMap,
                     Map<String, SocketChannel> channelMap,
                     Callback callback) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
        this.deviceMap = deviceMap;
        this.channelMap = channelMap;
        this.callback = callback;
    }

    @Override
    public void run() {
        while (ControlPower.serverStatus) {
            try {
                int readChannels = selector.select(2000);

                if (readChannels <= 0) {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (!key.isValid()) {
                    key.cancel();
                    continue;
                }
                try {
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        if (null == socketChannel) {
                            continue;
                        }
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);

                        InetSocketAddress remoteAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
                        String hostAddress = remoteAddress.getAddress().getHostAddress();

                        log.info("新连接【{}]", socketChannel);
                        if (channelMap.containsKey(hostAddress)) {
                            channelMap.get(hostAddress).close();
                            channelMap.remove(hostAddress);
                        }
                        channelMap.put(hostAddress, socketChannel);

                        // 发送心跳  获取mac地址
                        RequestParam param = new RequestParam();
                        param.setType(MessageType.HEART_BURN);
                        param.setIp(hostAddress);
                        //发送心跳之前先把清除缓存
                        MessageCache.getInstance().clearMac(hostAddress);
                        sender.sendMessage(param);
                        new GetMac(hostAddress).start();

                    }
                    if (key.isReadable()) {
                        buffer.clear();
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        if (!socketChannel.isConnected()) {
                            continue;
                        }
                        try {
                            socketChannel.read(buffer);
                            InetSocketAddress remoteAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
                            String hostAddress = remoteAddress.getAddress().getHostAddress();
                            ReceiveParam receiveParam = new ReceiveParam();
                            receiveParam.setBuffer(buffer);
                            receiveParam.setIp(hostAddress);
                            processor.receiveMessage(receiveParam);
                        } catch (IOException e) {
                            log.error("连接异常{}",e);
                            socketChannel.close();
                        }
                    }
                } catch (IOException e) {
                    log.info("===========监听连接事件失败{}",e);
                }
                keyIterator.remove();
            }
        }
    }

    class GetMac extends Thread {

        private String ip;

        public GetMac(String ip) {
            this.ip = ip;
        }

        @Override
        public void run() {
            int time = 0;
            String mac = null;
            while (time < 10 && null == mac) {
                try {
                    mac = MessageCache.getInstance().getMac(ip);
                    Thread.sleep(1000);
                    time++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (null != mac) {
                DeviceInfo deviceInfo = MessageCache.getInstance().getDevice(mac);
                if (null != deviceInfo) {
                    deviceInfo.setIpAddr(ip);
                    deviceMap.put(mac, deviceInfo);

                    Device device = new Device();
                    device.setMacAddr(mac);
                    device.setIpAddr(ip);
                    if (null != callback) {
                        ControlPower.executorService.execute(() -> callback.online(device));
                    }
                }
            } else {
                SocketChannel socketChannel = channelMap.get(ip);
                if (null != socketChannel) {
                    try {
                        socketChannel.close();
                        log.info("设备【ip：{}】连接异常，已关闭", ip);
                    } catch (IOException e) {
                        log.info("连接通道关闭异常..............");
                    }
                    channelMap.remove(ip);
                    log.info("未收到心跳回复，连接已关闭");
                }
            }
        }
    }

    public ServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }

    public void setServerSocketChannel(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public Map<String, SocketChannel> getChannelMap() {
        return channelMap;
    }

    public void setChannelMap(Map<String, SocketChannel> channelMap) {
        this.channelMap = channelMap;
    }

    public Map<String, DeviceInfo> getDeviceMap() {
        return deviceMap;
    }

    public void setDeviceMap(Map<String, DeviceInfo> deviceMap) {
        this.deviceMap = deviceMap;
    }
}
