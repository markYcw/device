package com.kedacom.power.message;

import com.kedacom.power.common.DataPack;
import com.kedacom.power.entity.DeviceInfo;
import com.kedacom.power.utils.BufferUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
@Slf4j
public class MessageSender {

    public void consumerMessage(RequestParam param) throws IOException {
        senderMap.get(param.getType()).send(this, param);
    }

    public void sendMessage(RequestParam param) {
        queue.offer(param);
    }

    static Map<Integer, MySender> senderMap = new HashMap<>();

    static {
        senderMap.put(MessageType.VOLTAGE, MyEnumSender.VOLTAGE_SENDER);
        senderMap.put(MessageType.ELECTRIC, MyEnumSender.ELECTRIC_SENDER);
        senderMap.put(MessageType.TEMPERATURE, MyEnumSender.TEMPERATURE_SENDER);
        senderMap.put(MessageType.HEART_BURN, MyEnumSender.HEART_BURN_SENDER);
        senderMap.put(MessageType.CHANNEL_STATUS, MyEnumSender.CHANNEL_STATUS);
        senderMap.put(MessageType.TURN_ONE, MyEnumSender.TURN_ONE);
        senderMap.put(MessageType.TURN_MANY, MyEnumSender.TURN_MANY);
    }

    private final BlockingQueue<RequestParam> queue = new LinkedBlockingQueue<>(50);

    private Map<String, SocketChannel> channelMap;

    private Map<String, DeviceInfo> deviceMap;

    private final ByteBuffer buffer = ByteBuffer.allocate(50);

    private MessageSender() {
    }

    private static volatile MessageSender instance;

    public static MessageSender getInstance() {
        if (null == instance){
            synchronized (MessageSender.class){
                if(null == instance){
                    instance = new MessageSender();
                }
            }
        }
        return instance;
    }

    public void init(Map<String, SocketChannel> channelMap, Map<String, DeviceInfo> deviceMap) {
        this.channelMap = channelMap;
        this.deviceMap = deviceMap;
        consumerStatus = true;
    }

    public static boolean consumerStatus = true;

    public void startConsumer() {
        log.info("????????????????????????..............");
        MessageConsumer messageConsumer = new MessageConsumer(queue, instance);
        new Thread(messageConsumer).start();
    }

    public void clearQueue() {
        queue.clear();
    }

    interface MySender {
        /**
         * ??????????????????
         *
         * @param messageSender
         * @param param
         * @throws IOException
         */
        void send(MessageSender messageSender, RequestParam param) throws IOException;
    }

    enum MyEnumSender implements MySender {
        //????????????
        VOLTAGE_SENDER {
            @Override
            public void send(MessageSender messageSender, RequestParam param) throws IOException {
                messageSender.sendForVoltage(param);
            }
        },
        //????????????
        ELECTRIC_SENDER {
            @Override
            public void send(MessageSender messageSender, RequestParam param) throws IOException {
                messageSender.sendForElectric(param);
            }
        },
        //????????????
        TEMPERATURE_SENDER {
            @Override
            public void send(MessageSender messageSender, RequestParam param) throws IOException {
                messageSender.sendForTemperature(param);
            }
        },
        //??????
        HEART_BURN_SENDER {
            @Override
            public void send(MessageSender messageSender, RequestParam param) throws IOException {
                messageSender.sendForHeartBurn(param);
            }
        },
        //??????????????????
        CHANNEL_STATUS {
            @Override
            public void send(MessageSender messageSender, RequestParam param) throws IOException {
                messageSender.sendForChannelStatus(param);
            }
        },
        //???????????????
        TURN_ONE {
            @Override
            public void send(MessageSender messageSender, RequestParam param) throws IOException {
                messageSender.sendForTurnOne(param);
            }
        },
        //???????????????
        TURN_MANY {
            @Override
            public void send(MessageSender messageSender, RequestParam param) throws IOException {
                messageSender.sendForTurnMany(param);
            }
        }
    }

    private void sendForVoltage(RequestParam param) throws IOException {
        log.info("????????????????????????????????????mac???{}???", param.getMac());
        param.setFunctionMark((byte) 0x03);
        param.setDataAddr(DataPack.voltage);
        param.setDataLength((byte) 0x01);

        sendRequest(param);
    }

    private void sendForElectric(RequestParam param) throws IOException {
        log.info("????????????????????????????????????mac???{}???", param.getMac());
        param.setFunctionMark((byte) 0x03);
        param.setDataAddr(DataPack.electricity);
        param.setDataLength((byte) 0x08);

        sendRequest(param);
    }

    private void sendForTemperature(RequestParam param) throws IOException {
        log.info("????????????????????????????????????mac???{}???", param.getMac());
        param.setFunctionMark((byte) 0x03);
        param.setDataAddr(DataPack.temperature);
        param.setDataLength((byte) 0x01);

        sendRequest(param);
    }

    private void sendForHeartBurn(RequestParam param) throws IOException {

        log.debug("?????????????????????mac???{}???", param.getMac());

        String mac = param.getMac();
        if (null != mac && !"".equals(mac)) {
            param.setFunctionMark((byte) 0x03);
            param.setDataAddr(DataPack.heart);
            param.setDataLength((byte) 0x01);

            sendRequest(param);

        } else {
            //????????????????????????????????????????????????????????????
            SocketChannel socketChannel = channelMap.get(param.getIp());
            if (null == socketChannel || !socketChannel.isConnected()) {
                return;
            }

            buffer.clear();
            buffer.put(DataPack.header);
            buffer.put(new byte[10]);
            buffer.put((byte) 0x03);
            buffer.put(DataPack.heart);
            buffer.put((byte) 0x01);
            buffer.put((byte) 0x73);
            buffer.put(DataPack.token);
            buffer.put(DataPack.end);
            buffer.flip();
            socketChannel.write(buffer);
        }

    }

    private void sendForChannelStatus(RequestParam param) throws IOException {
        log.info("??????????????????????????????????????????mac???{}???", param.getMac());
        param.setFunctionMark((byte) 0x03);
        param.setDataAddr(DataPack.channels);
        param.setDataLength((byte) 0x01);

        sendRequest(param);
    }

    private void sendForTurnOne(RequestParam param) throws IOException {
        log.info("???????????????????????????????????????mac???{}???channel???{}???status???{}???", param.getMac(), param.getChannel(), param.getChannelStatus());
        param.setFunctionMark((byte) 0x06);
        param.setDataAddr(DataPack.onOrOff);
        param.setDataLength((byte) 0x01);
        byte[] controlParam = new byte[2];
        controlParam[0] = (byte) param.getChannel();
        controlParam[1] = param.getChannelStatus() ? (byte) 1 : (byte) 0;
        param.setControlParam(controlParam);

        sendRequest(param);
    }

    private void sendForTurnMany(RequestParam param) throws IOException {
        log.info("???????????????????????????????????????mac???{}??????channel???{}???", param.getMac(), param.getStatusMap());
        param.setFunctionMark((byte) 0x06);
        param.setDataAddr(DataPack.channels);
        param.setDataLength((byte) 0x01);

        byte[] controlParam = new byte[2];
        controlParam[1] = channelStateByte(param.getStatusMap());
        param.setControlParam(controlParam);

        sendRequest(param);
    }

    private byte channelStateByte(Map<Integer, Integer> map) {
        int size = map.size();
        char[] chars = new char[size];
        Arrays.fill(chars, '0');
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            chars[size - entry.getKey()] = entry.getValue().toString().charAt(0);
        }
        return BufferUtils.binToHex(new String(chars));
    }

    private void sendRequest(RequestParam param) throws IOException {
        String mac = param.getMac();

        requestBuffer(buffer, param);
        DeviceInfo deviceInfo = deviceMap.get(mac);
        if (null == deviceInfo) {
            return;
        }
        SocketChannel socketChannel = channelMap.get(deviceInfo.getIpAddr());
        if (null != socketChannel && socketChannel.isConnected()) {
            socketChannel.write(buffer);
        }
    }

    private void requestBuffer(ByteBuffer buffer, RequestParam param) {

        buffer.clear();

        DeviceInfo deviceInfo = deviceMap.get(param.getMac());
        if (null == deviceInfo) {
            return;
        }
        int type = deviceInfo.getType();

        //??????
        buffer.put(DataPack.header);

        //id
        byte[] id = deviceInfo.getId();
        if (null != id) {
            buffer.put(id);
        } else {
            buffer.put(new byte[10]);
        }

        //?????????
        buffer.put(param.getFunctionMark());
        //????????????
        buffer.put(param.getDataAddr());
        //????????????
        buffer.put(param.getDataLength());
        //????????????
        if (null != param.getControlParam()) {
            buffer.put(param.getControlParam());
        }

        //???????????????????????????????????????
        int position = buffer.position();
        buffer.put((byte) 0x00);
        //token
        buffer.put(DataPack.token);

        //??????
        buffer.put(DataPack.end);

        buffer.flip();
        //???????????????????????????
        checkSum(buffer, position);

        buffer.rewind();
    }

    private static void checkSum(ByteBuffer buffer, int position) {
        byte[] array = buffer.array();
        int sum = 0;
        for (int i = 4; i < position; i++) {
            sum += array[i];
        }
        buffer.position(position);
        buffer.put((byte) (sum & 0xff));
    }
}
