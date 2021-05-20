package com.kedacom.device.core.notify;

import com.kedacom.device.core.convert.StreamMediaConvert;
import com.kedacom.device.core.entity.TransDataEntity;
import com.kedacom.device.core.event.TransDataNotifyEvent;
import com.kedacom.device.core.kafka.DeviceKafkaProduceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: hxj
 * @Date: 2021/5/20 15:48
 */
@Slf4j
@Component
public class StreamMediaEventListener {

    @Autowired
    private StreamMediaConvert streamMediaConvert;
    @Autowired
    private DeviceKafkaProduceService kafkaProduceService;

    @EventListener
    public void transDataNotify(TransDataNotifyEvent event) {
        log.info("接收透明通道数据通知:{}", event);

        TransDataEntity entity = streamMediaConvert.convertTransDataNotifyEvent(event);

        kafkaProduceService.sendTransDataNotifyKafka(entity);
    }

}
