package com.kedacom.device.core.message;

import com.kedacom.device.core.kafka.UmsProducerTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import sun.rmi.runtime.Log;

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
     * @param umsSubDeviceStatusModelStr
     * @return
     */
    public ListenableFuture<SendResult<Object, Object>> deviceStatusUpdate(String umsSubDeviceStatusModelStr) {

        log.info("--------kafka开始推送设备状态改变通知--------");
        return kafkaTemplate.send(UmsProducerTopic.DEVICE_STATUS_CHANGE, umsSubDeviceStatusModelStr);
    }

}
