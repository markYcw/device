package com.kedacom.device.core.notify;

import com.kedacom.device.core.convert.StreamMediaConvert;
import com.kedacom.device.core.entity.TransDataEntity;
import com.kedacom.device.core.event.TransDataNotifyEvent;
import com.kedacom.device.core.kafka.UmsKafkaMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

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
    private StreamMediaConvert streamMediaConvert;

    @Autowired
    private UmsKafkaMessageProducer kafkaMessageProducer;

    private final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10), new CustomizableThreadFactory("StreamMediaEventListener-"));

    @EventListener
    public void transDataNotify(TransDataNotifyEvent event) {
        log.info("接收透明通道数据通知:{}", event);

        TransDataEntity entity = streamMediaConvert.convertTransDataNotifyEvent(event);

        poolExecutor.submit(() -> {
            kafkaMessageProducer.sendTransDataNotifyKafka(entity);
        });

    }

}
