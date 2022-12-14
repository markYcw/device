package com.kedacom.device.core.kafka;

import com.kedacom.device.core.entity.TransDataEntity;
import com.kedacom.kafka.UmsProducerTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/20
 */
@Slf4j
@Component
public class UmsKafkaMessageProducer {

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    /**
     * 设备状态改变通知
     *
     * @param umsSubDeviceStatusModelStr
     * @return
     */
    public ListenableFuture<SendResult<Object, Object>> deviceStatusUpdate(String umsSubDeviceStatusModelStr) {

        log.info("--------kafka开始推送设备状态改变通知--------");
        return kafkaTemplate.send(UmsProducerTopic.DEVICE_STATUS_CHANGE, umsSubDeviceStatusModelStr);
    }

    /**
     * 设备更新通知
     *
     * @param umsSubDeviceChangeModelStr
     * @return
     */
    public ListenableFuture<SendResult<Object, Object>> deviceChange(String umsSubDeviceChangeModelStr) {

        log.info("--------kafka开始推送设备更新改变通知--------");
        return kafkaTemplate.send(UmsProducerTopic.DEVICE_CHANGE, umsSubDeviceChangeModelStr);
    }

    public void sendTransDataNotifyKafka(TransDataEntity entity) {
        log.info("接收透明通道数据通知进行推送:{}", entity);
        ListenableFuture<SendResult<Object, Object>> future;
        future = kafkaTemplate.send(UmsProducerTopic.DEVICE_TRANS_DATA_NOTIFY, entity.toString());
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

    /**
     * 调度组状态通知
     *
     * @param scheduleStatusEventStr
     * @return
     */
    public ListenableFuture<SendResult<Object, Object>> scheduleStatusUpdate(String scheduleStatusEventStr) {

        log.info("--------kafka开始推送调度组状态通知--------");
        return kafkaTemplate.send(UmsProducerTopic.DEVICE_SCHEDULE_GROUP_STATUS_NTY, scheduleStatusEventStr);
    }

    public ListenableFuture<SendResult<Object, Object>> deviceGroupStatus(String model) {

        log.info("--------kafka开始设备分组状态通知--------");
        return kafkaTemplate.send(UmsProducerTopic.DEVICE_GROUP_STATUS_NTY, model);
    }

    public ListenableFuture<SendResult<Object, Object>> deviceAndGroupStatus(String model) {

        log.info("--------kafka开始设备与分组关联状态变更通知--------");
        return kafkaTemplate.send(UmsProducerTopic.DEVICE_AND_GROUP_STATUS_NTY, model);
    }
}


