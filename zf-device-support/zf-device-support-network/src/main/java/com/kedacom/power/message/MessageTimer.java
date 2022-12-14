package com.kedacom.power.message;

import com.kedacom.power.entity.DeviceInfo;
import lombok.extern.slf4j.Slf4j;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
@Slf4j
public class MessageTimer extends TimerTask {

    private Map<String , SocketChannel> channelMap;

    private Map<String , DeviceInfo> deviceMap;

    private MessageSender messageSender = MessageSender.getInstance();

    public void init(Map<String , SocketChannel> channelMap, Map<String , DeviceInfo> deviceMap) {
        this.channelMap = channelMap;
        this.deviceMap = deviceMap;
    }


    @Override
    public void run() {
        try {
            log.info("channel:{}, device:{}", channelMap, deviceMap);

            if (null != channelMap && channelMap.size() > 0) {

                Iterator<Map.Entry<String, DeviceInfo>> iterator = deviceMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, DeviceInfo> deviceEntry = iterator.next();
                    String key = deviceEntry.getKey();
                    DeviceInfo value = deviceEntry.getValue();
                    SocketChannel socketChannel = channelMap.get(value.getIpAddr());
                    if (null == socketChannel || !socketChannel.isConnected()) {
                        channelMap.remove(value.getIpAddr());
                        iterator.remove();
                    }

                    //发消息,依次获取设备的信息
                    log.info("设备【{}】在线，发送心跳", key);
                    RequestParam param = new RequestParam();
                    param.setType(MessageType.HEART_BURN);
                    param.setMac(key);
                    messageSender.sendMessage(param);
                }
            }
        } catch (Exception e) {
            log.error("======心跳线程异常",e);
        }
    }
}
