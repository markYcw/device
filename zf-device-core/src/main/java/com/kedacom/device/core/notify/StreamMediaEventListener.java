package com.kedacom.device.core.notify;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.kedacom.api.WebsocketFeign;
import com.kedacom.device.core.convert.StreamMediaConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.entity.TransDataEntity;
import com.kedacom.device.core.event.AlarmEvent;
import com.kedacom.device.core.event.AudioActEvent;
import com.kedacom.device.core.event.BurnStateEvent;
import com.kedacom.device.core.event.TransDataNotifyEvent;
import com.kedacom.device.core.kafka.UmsKafkaMessageProducer;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.service.StreamMediaService;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.deviceListener.msgType.MsgType;
import com.kedacom.deviceListener.notify.sm.AlarmDTO;
import com.kedacom.deviceListener.notify.sm.BurnStateDTO;
import com.kedacom.pojo.SystemWebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: hxj
 * @Date: 2021/5/20 15:48
 */
@Slf4j
@Component
public class StreamMediaEventListener {

    @Autowired
    private WebsocketFeign websocketFeign;

    @Autowired
    private StreamMediaConvert streamMediaConvert;

    @Autowired
    private UmsKafkaMessageProducer kafkaMessageProducer;

    @Autowired
    private DeviceNotifyUtils notifyUtils;

    @Autowired
    private RegisterListenerService registerListenerService;

    @Autowired
    private StreamMediaService streamMediaService;

    private final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10), new CustomizableThreadFactory("StreamMediaEventListener-"));

    @EventListener
    public void transDataNotify(TransDataNotifyEvent event) {
        log.info("??????????????????????????????:{}", event);

        TransDataEntity entity = streamMediaConvert.convertTransDataNotifyEvent(event);

        poolExecutor.submit(() -> {
            kafkaMessageProducer.sendTransDataNotifyKafka(entity);
        });

    }

    @EventListener(AudioActEvent.class)
    public void audioActNotify(AudioActEvent event){

        log.info("????????????????????????:{}", event);
       /* AudioActDTO audioActDTO = streamMediaConvert.convertToAudioActDTO(event);
        Integer ssid = event.getNty().getSsid();
        DeviceInfoEntity bySsid = streamMediaService.getBySsid(ssid);
        String id = bySsid.getId();
        audioActDTO.setMsgType(MsgType.S_M_AUDIO_ACT_NTY.getType());
        //??????webSocket?????????
        SystemWebSocketMessage message = new SystemWebSocketMessage();
        message.setOperationType(4);
        message.setServerName("device");
        message.setData(audioActDTO);
        websocketFeign.sendInfo(JSON.toJSONString(message));
        List<KmListenerEntity> all = registerListenerService.getAll(MsgType.S_M_AUDIO_ACT_NTY.getType());
        if(!CollectionUtil.isEmpty(all)){
            for (KmListenerEntity kmListenerEntity : all) {
                try {
                    notifyUtils.audioActNty(kmListenerEntity.getUrl(),audioActDTO);
                } catch (Exception e) {
                    log.error("------------??????????????????????????????????????????",e);
                }
            }
        }*/
    }

    @EventListener(BurnStateEvent.class)
    public void burnStateNotify(BurnStateEvent event){

        log.info("??????????????????:{}", event);
        BurnStateDTO burnStateDTO = streamMediaConvert.convertToBurnStateDTO(event);
        Integer ssid = event.getNty().getSsid();
        DeviceInfoEntity bySsid = streamMediaService.getBySsid(ssid);
        String id = bySsid.getId();
        burnStateDTO.setMsgType(MsgType.S_M_BURN_STATE_NTY.getType());
        //??????webSocket?????????
        SystemWebSocketMessage message = new SystemWebSocketMessage();
        message.setOperationType(5);
        message.setServerName("device");
        message.setData(burnStateDTO);
        log.info("===============??????webSocket?????????{}",JSON.toJSONString(message));
        websocketFeign.sendInfo(JSON.toJSONString(message));
        List<KmListenerEntity> all = registerListenerService.getAll(MsgType.S_M_BURN_STATE_NTY.getType());
        if(!CollectionUtil.isEmpty(all)){
            for (KmListenerEntity kmListenerEntity : all) {
                try {
                    notifyUtils.burnStateNty(kmListenerEntity.getUrl(),burnStateDTO);
                } catch (Exception e) {
                    log.error("------------??????????????????????????????????????????",e);
                }
            }
        }
    }

    @EventListener(AlarmEvent.class)
    public void alarmNotify(AlarmEvent event){

        log.info("??????????????????:{}", event);
        AlarmDTO alarmDTO = event.acquireData(AlarmDTO.class);
        Integer ssid = event.getNty().getSsid();
        DeviceInfoEntity bySsid = streamMediaService.getBySsid(ssid);
        String id = bySsid.getId();
        alarmDTO.setMsgType(MsgType.S_M_ALARM_NTY.getType());
        //??????webSocket?????????
        SystemWebSocketMessage message = new SystemWebSocketMessage();
        message.setOperationType(6);
        message.setServerName("device");
        message.setData(alarmDTO);
        log.info("===============??????webSocket?????????{}",JSON.toJSONString(message));
        websocketFeign.sendInfo(JSON.toJSONString(message));
        List<KmListenerEntity> all = registerListenerService.getAll(MsgType.S_M_ALARM_NTY.getType());
        if(!CollectionUtil.isEmpty(all)){
            for (KmListenerEntity kmListenerEntity : all) {
                try {
                    notifyUtils.alarmNty(kmListenerEntity.getUrl(),alarmDTO);
                } catch (Exception e) {
                    log.error("------------??????????????????????????????????????????",e);
                }
            }
        }
    }

}
