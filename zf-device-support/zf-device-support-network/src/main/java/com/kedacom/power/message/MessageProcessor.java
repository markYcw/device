package com.kedacom.power.message;

import com.kedacom.power.ControlPower;
import com.kedacom.power.callback.Callback;
import com.kedacom.power.common.DataPack;
import com.kedacom.power.entity.Device;
import com.kedacom.power.entity.DeviceInfo;
import com.kedacom.power.utils.BufferUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
@Slf4j
public class MessageProcessor {

    private Map<String, DeviceInfo> deviceMap;

    private Map<String, SocketChannel> channelMap;

    private Map<String, Timer> timerMap;

    private Callback callback;

    private static final MessageProcessor MESSAGE_PROCESSOR = new MessageProcessor();

    private MessageProcessor() {
    }

    public static MessageProcessor getInstance() {
        return MESSAGE_PROCESSOR;
    }

    public void init(Map<String, DeviceInfo> deviceMap,
                     Map<String, SocketChannel> channelMap,
                     Map<String, Timer> timerMap,
                     Callback callback) {
        this.deviceMap = deviceMap;
        this.channelMap = channelMap;
        this.timerMap = timerMap;
        this.callback = callback;
    }

    static Map<Short, MyHandler> handlerMap = new HashMap<>();

    static {
        handlerMap.put(MessageAddress.VOLTAGE, MyEnumHandler.VOLTAGE_HANDLER);
        handlerMap.put(MessageAddress.ELECTRIC, MyEnumHandler.ELECTRIC_HANDLER);
        handlerMap.put(MessageAddress.TEMPERATURE, MyEnumHandler.TEMPERATURE_HANDLER);
        handlerMap.put(MessageAddress.HEART_BURN, MyEnumHandler.HEART_BURN_HANDLER);
        handlerMap.put(MessageAddress.CHANNEL_STATUS, MyEnumHandler.CHANNEL_STATUS_HANDLER);
        handlerMap.put(MessageAddress.TURN_CHANNEL, MyEnumHandler.CHANNEL_STATUS_HANDLER);
    }

    public void receiveMessage(ReceiveParam param) {
        ByteBuffer buffer = param.getBuffer();

        //校验token
        buffer.flip();
        int limit = buffer.limit();
        if (limit - 6 < 0) {
            return;
        }
        buffer.position(limit - 6);
        byte[] bytes2 = new byte[2];
        buffer.get(bytes2);
        if (!Arrays.equals(bytes2, DataPack.token)) {
            return;
        }

        //定位到数据地址
        //定位到功能码的位置判断此次可读事件是获取还是控制
        buffer.position(14);
        byte b = buffer.get();
        if (b == 3) {
            buffer.position(16);
        } else {
            buffer.position(15);
        }
        short address = buffer.getShort();

        handlerMap.get(address).handle(this, param);

    }

    interface MyHandler {
        /**
         * 消息处理
         * @param messageProcessor
         * @param param
         */
        void handle(MessageProcessor messageProcessor, ReceiveParam param);
    }

    private enum MyEnumHandler implements MyHandler {
        //处理电压
        VOLTAGE_HANDLER {
            @Override
            public void handle(MessageProcessor messageProcessor, ReceiveParam param) {
                messageProcessor.handleVoltage(param);
            }
        },
        //处理电流
        ELECTRIC_HANDLER {
            @Override
            public void handle(MessageProcessor messageProcessor, ReceiveParam param) {
                messageProcessor.handleElectric(param);
            }
        },
        //处理温度
        TEMPERATURE_HANDLER {
            @Override
            public void handle(MessageProcessor messageProcessor, ReceiveParam param) {
                messageProcessor.handleTemperature(param);
            }
        },
        //处理心跳
        HEART_BURN_HANDLER {
            @Override
            public void handle(MessageProcessor messageProcessor, ReceiveParam param) {
                messageProcessor.handHeartBurn(param);
            }
        },
        //处理通道状态
        CHANNEL_STATUS_HANDLER {
            @Override
            public void handle(MessageProcessor messageProcessor, ReceiveParam param) {
                messageProcessor.handleChannelStatus(param);
            }
        }
    }

    void handleVoltage(ReceiveParam param) {
        ByteBuffer buffer = param.getBuffer();
        String mac = BufferUtils.getMac(buffer);

        buffer.position(18);
        int voltage = buffer.getShort();

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setVoltage(voltage / 100);
        log.info("获取电压指令回复【mac：{}, voltage：{}】", mac, voltage / 100);
        MessageCache.getInstance().setDevice(mac, deviceInfo);
    }

    void handleElectric(ReceiveParam param) {
        ByteBuffer buffer = param.getBuffer();
        String mac = BufferUtils.getMac(buffer);

        buffer.position(18);

        if (null != deviceMap && deviceMap.containsKey(mac)) {
            DeviceInfo deviceInfo = deviceMap.get(mac);
            Map<Integer, Float> electricityMap = new HashMap<>(8);

            int channels = deviceInfo.getChannels();
            for (int i = 0; i < channels; i++) {
                float electricity = buffer.getShort();
                electricityMap.put(i + 1, electricity / 1000);
            }
            deviceInfo.setElectricityMap(electricityMap);
            log.info("获取电流指令回复【mac：{}, electric：{}】", mac, electricityMap);
            MessageCache.getInstance().setDevice(mac, deviceInfo);
        }
    }

    void handleTemperature(ReceiveParam param) {
        ByteBuffer buffer = param.getBuffer();
        String mac = BufferUtils.getMac(buffer);

        buffer.position(18);
        int temperature = buffer.getShort();

        DeviceInfo deviceInfo = deviceMap.get(mac);
        deviceInfo.setTemperature(temperature / 100);
        log.info("获取温度指令回复【mac：{}, temperature：{}】", mac, deviceInfo.getTemperature());
        MessageCache.getInstance().setDevice(mac, deviceInfo);
    }

    void handleChannelStatus(ReceiveParam param) {
        ByteBuffer buffer = param.getBuffer();
        String mac = BufferUtils.getMac(buffer);




        buffer.position(14);
        byte b = buffer.get();
        if (b == 3) {
            MessageCache.getInstance().clear(mac);
            DeviceInfo deviceInfo = new DeviceInfo();

            buffer.position(19);

            String bin = BufferUtils.hexToBin(buffer.get());
            char[] states = bin.toCharArray();
            int mapLen = 0;
            deviceInfo = deviceMap.get(mac);
            if (deviceInfo.getType() == 1) {
                mapLen = 8;
            } else {
                mapLen = 4;
            }
            Map<Integer, Integer> map = new HashMap<>(mapLen);
            for (int i = states.length - 1, j = 0; j < mapLen; i--, j++) {
                map.put(j + 1, Integer.parseInt(String.valueOf(states[i])));
            }
            deviceInfo.setChannelStatus(map);
            log.info("获取通道状态指令回复【mac：{}, status：{}】", mac, map);
            MessageCache.getInstance().setDevice(mac, deviceInfo);

        } else {
            MessageCache.getInstance().clearOperation(mac);
            buffer.position(17);
            byte result = buffer.get();
            System.out.println("操作结果: " + result);
            log.info("控制通道指令回复【mac：{}, result：{}】", mac, result);
            MessageCache.getInstance().setOperation(mac, result == 1);
        }

    }

    void handHeartBurn(ReceiveParam param) {
        ByteBuffer buffer = param.getBuffer();
        String mac = BufferUtils.getMac(buffer);

        DeviceInfo deviceInfo = deviceMap.get(mac);

        if (null == deviceInfo) {

            deviceInfo = new DeviceInfo();
            deviceInfo.setMacAddr(mac);
            deviceMap.put(mac, deviceInfo);

            //设备唯一ID
            buffer.position(4);
            byte[] bytes10 = new byte[10];
            buffer.get(bytes10);
            deviceInfo.setId(bytes10);

            buffer.position(buffer.position() + 5);
            byte cByte = buffer.get();
            if (cByte == 1) {
                deviceInfo.setChannels(4);
                deviceInfo.setType(2);
            } else if (cByte == 2) {
                deviceInfo.setChannels(8);
                deviceInfo.setType(1);
            } else {
                return;
            }

        }
        log.info("获取心跳指令回复【mac：{}, type：{}】", mac, deviceInfo.getType());
        MessageCache.getInstance().setMac(param.getIp(), mac);
        MessageCache.getInstance().setDevice(mac, deviceInfo);


        //定时器
        Timer timer = timerMap.get(mac);
        if (null != timer) {
            timer.cancel();
        }
        timer = new Timer();
        DeviceInfo finalDeviceInfo = deviceInfo;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SocketChannel socketChannel = channelMap.get(finalDeviceInfo.getIpAddr());
                if (null != socketChannel) {
                    try {
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    channelMap.remove(finalDeviceInfo.getIpAddr());
                }
                if (null != callback) {
                    Device device = new Device();
                    device.setIpAddr(finalDeviceInfo.getIpAddr());
                    device.setMacAddr(finalDeviceInfo.getMacAddr());
                    ControlPower.executorService.execute(() -> callback.offline(device));
                }
                deviceMap.remove(mac);
            }
        }, 30 * 1000);
        timerMap.put(mac, timer);
    }

}

