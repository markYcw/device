package com.kedacom.device.core.kafka;

import com.kedacom.device.core.constant.TopicConstant;
import com.kedacom.device.core.entity.TransDataEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @Auther: hxj
 * @Date: 2021/5/20 16:13
 */
@Component
@Slf4j
public class DeviceKafkaProduceService {

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public void sendTransDataNotifyKafka(TransDataEntity entity) {
        log.info("接收透明通道数据通知进行推送:{}", entity);
        ListenableFuture<SendResult<Object, Object>> future;
        future = kafkaTemplate.send(TopicConstant.DEVICE_TRANS_DATA_NOTIFY, entity.toString());
        future.addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("接收透明通道数据通知进行推送失败:{}", ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<Object, Object> result) {
                log.info("接收透明通道数据通知进行推送成功:{}", result.toString());
            }
        });
    }
}
