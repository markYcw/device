package com.kedacom.device.core.utils;


import com.alibaba.fastjson.JSON;
import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author ycw
 * @date 2021/6/28 11:13
 */
@Slf4j
@Component
public class DeviceNotifyUtils {

    @Autowired
    private RemoteRestTemplate remoteRestTemplate;


    /**
     * 音频功率通知
     * @param url 接收方URL
     * @return
     */
    public void audioActNty(String url,DeviceNotifyRequestDTO dto){
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(dto), remoteRestTemplate.getHttpHeaders());
        remoteRestTemplate.getRestTemplate().postForObject(url, httpEntity, String.class);
    }

    /**
     * 刻录状态通知
     * @param url  接收方URL
     * @param dto
     */
    public void burnStateNty(String url,DeviceNotifyRequestDTO dto){
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(dto), remoteRestTemplate.getHttpHeaders());
        remoteRestTemplate.getRestTemplate().postForObject(url, httpEntity, String.class);
    }

    /**
     * 异常告警通知
     * @param url  接收方URL
     * @param dto
     */
    public void alarmNty(String url,DeviceNotifyRequestDTO dto){
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(dto), remoteRestTemplate.getHttpHeaders());
        remoteRestTemplate.getRestTemplate().postForObject(url, httpEntity, String.class);
    }

    /**
     * 发送设备掉线通知
     * @param url  接收方URL
     * @param dto
     */
    public void offLineNty(String url,DeviceNotifyRequestDTO dto){
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(dto), remoteRestTemplate.getHttpHeaders());
        remoteRestTemplate.getRestTemplate().postForObject(url, httpEntity, String.class);
    }

    /**
     * 监控平台异常告警通知
     * @param url  接收方URL
     * @param dto
     */
    public void cuAlarmNty(String url,DeviceNotifyRequestDTO dto){
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(dto), remoteRestTemplate.getHttpHeaders());
        remoteRestTemplate.getRestTemplate().postForObject(url, httpEntity, String.class);
    }


}
