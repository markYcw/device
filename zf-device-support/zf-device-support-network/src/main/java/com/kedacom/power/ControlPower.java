package com.kedacom.power;

import cn.hutool.core.util.ObjectUtil;
import com.kedacom.power.callback.Callback;
import com.kedacom.power.entity.Device;
import com.kedacom.power.entity.DeviceInfo;
import com.kedacom.power.link.TcpLink;
import com.kedacom.power.message.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.*;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
@Slf4j
public class ControlPower {

    public static ExecutorService executorService =
            new ThreadPoolExecutor(
                    1,
                    5,
                    10L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(5),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private Map<String, SocketChannel> channelMap = new ConcurrentHashMap<>(16);
    private Map<String, DeviceInfo> deviceMap = new ConcurrentHashMap<>(16);
    private Map<String, Timer> timerMap = new ConcurrentHashMap<>(16);
    private MessageSender messageSender;
    private MessageProcessor messageProcessor;
    private Callback callback;
    private Timer timer;

    public static boolean serverStatus = false;

    public static boolean serverState = false;

    private static final ControlPower CONTROL_POWER = new ControlPower();

    public static ControlPower getInstance() {
        return CONTROL_POWER;
    }

    private ControlPower() {
    }

    public void startServer(int port) throws IOException {
        if (serverStatus) {
            throw new RuntimeException("请停止之前的服务再启动新服务");
        }

        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));

        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        messageProcessor = MessageProcessor.getInstance();
        messageProcessor.init(deviceMap, channelMap, timerMap, callback);

        messageSender = MessageSender.getInstance();
        log.info("===========初始化MessageSender的channelMap：{}" + channelMap);
        messageSender.init(channelMap, deviceMap);
        messageSender.startConsumer();

        serverStatus = true;
        TcpLink tcpLink = new TcpLink();
        tcpLink.init(serverSocketChannel, selector, deviceMap, channelMap, callback);
        tcpLink.start();

        MessageTimer messageTimer = new MessageTimer();
        messageTimer.init(channelMap, deviceMap);
        long delay = 20 * 1000;
        long period = 10 * 1000;
        timer = new Timer();
        // task-所要安排的任务 time-首次执行任务的时间 period-执行一次task的时间间隔，单位毫秒
        // 第一次心跳在tcp启动20s后，之后每个10s执行一次定时任务
        timer.scheduleAtFixedRate(messageTimer, delay, period);

        log.info("server start with port {}", port);

    }

    public void startServer(int port, Callback callback) throws IOException {
        this.callback = callback;
        startServer(port);
    }

    public void stopServer() {
        serverStatus = false;

        MessageSender.consumerStatus = false;
        if (null != messageSender) {
            messageSender.clearQueue();
        }

        if (!channelMap.isEmpty()) {
            Iterator<Map.Entry<String, SocketChannel>> iterator = channelMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, SocketChannel> channelEntry = iterator.next();
                SocketChannel channel = channelEntry.getValue();
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                iterator.remove();
            }
        }
        if (!deviceMap.isEmpty()) {
            deviceMap.clear();
        }
        if (null != serverSocketChannel) {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            serverSocketChannel = null;
        }
        if (null != selector) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            selector = null;
        }
        if (!timerMap.isEmpty()) {
            Iterator<Map.Entry<String, Timer>> iterator = timerMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Timer> next = iterator.next();
                Timer timer = next.getValue();
                timer.cancel();
                iterator.remove();
            }
        }
        if (null != timer) {
            timer.cancel();
        }

        MessageCache.getInstance().clearAll();
    }


    /**
     * 温度
     *
     * @param mac
     * @return
     */
    public int getTemperature(String mac) {
        MessageCache.getInstance().clear(mac);

        RequestParam requestParam = new RequestParam();
        requestParam.setMac(mac);
        requestParam.setType(MessageType.TEMPERATURE);
        MessageSender.getInstance().sendMessage(requestParam);

        DeviceInfo deviceInfo = getDeviceInfo(mac);
        return null == deviceInfo ? 0 : deviceInfo.getTemperature();
    }

    private Boolean getOperation(String mac) {
        int time = 0;
        Boolean operation = null;
        while (time < 30 && null == operation) {
            try {
                operation = MessageCache.getInstance().getOperation(mac);
                Thread.sleep(100);
                time++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return operation;
    }

    private DeviceInfo getDeviceInfo(String mac) {
        int time = 0;
        DeviceInfo deviceInfo = null;
        while (time < 30 && null == deviceInfo) {
            try {
                deviceInfo = MessageCache.getInstance().getDevice(mac);
                Thread.sleep(100);
                time++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return deviceInfo;
    }

    public int getVoltage(String mac) {
        MessageCache.getInstance().clear(mac);

        RequestParam requestParam = new RequestParam();
        requestParam.setMac(mac);
        requestParam.setType(MessageType.VOLTAGE);
        MessageSender.getInstance().sendMessage(requestParam);

        DeviceInfo deviceInfo = getDeviceInfo(mac);
        return null == deviceInfo ? 0 : deviceInfo.getVoltage();
    }

    public Map<Integer, Float> getElectricity(String mac) {
        MessageCache.getInstance().clear(mac);

        RequestParam requestParam = new RequestParam();
        requestParam.setMac(mac);
        requestParam.setType(MessageType.ELECTRIC);
        MessageSender.getInstance().sendMessage(requestParam);

        DeviceInfo deviceInfo = getDeviceInfo(mac);
        return null == deviceInfo ? null : deviceInfo.getElectricityMap();
    }

    public Map<Integer, Integer> getChannelState(String mac) {
        MessageCache.getInstance().clear(mac);

        RequestParam requestParam = new RequestParam();
        requestParam.setMac(mac);
        requestParam.setType(MessageType.CHANNEL_STATUS);
        MessageSender.getInstance().sendMessage(requestParam);

        DeviceInfo deviceInfo = getDeviceInfo(mac);
        return null == deviceInfo ? null : deviceInfo.getChannelStatus();
    }

    public boolean turn(String mac, int channel, boolean flag) {
        MessageCache.getInstance().clearOperation(mac);

        RequestParam requestParam = new RequestParam();
        requestParam.setMac(mac);
        requestParam.setType(MessageType.TURN_ONE);
        requestParam.setChannel(channel);
        requestParam.setChannelStatus(flag);
        MessageSender.getInstance().sendMessage(requestParam);

        Boolean operation = getOperation(mac);
        return null != operation && operation;
    }

    public boolean turnMany(String mac, Map<Integer, Integer> stateMap) {
        MessageCache.getInstance().clearOperation(mac);

        RequestParam requestParam = new RequestParam();
        requestParam.setMac(mac);
        requestParam.setType(MessageType.TURN_MANY);
        requestParam.setStatusMap(stateMap);
        MessageSender.getInstance().sendMessage(requestParam);


        Boolean operation = getOperation(mac);
        return null != operation && operation;
    }

    public Map<String, Device> getDevices() {
        if (deviceMap.isEmpty()) {
            return null;
        }
        Map<String, Device> map = new ConcurrentHashMap<>(8);
        for (Map.Entry<String, DeviceInfo> entry : deviceMap.entrySet()) {
            DeviceInfo deviceInfo = entry.getValue();
            Device device = new Device();
            device.setDeviceName(deviceInfo.getDeviceName());
            device.setIpAddr(deviceInfo.getIpAddr());
            device.setMacAddr(deviceInfo.getMacAddr());
            if (ObjectUtil.isNotNull(deviceInfo.getChannels())) {
                device.setChannels(deviceInfo.getChannels());
            }
            map.put(deviceInfo.getMacAddr(), device);
        }
        return map;
    }

    public Map<String, DeviceInfo> getInfo() {
        return deviceMap;
    }


}
