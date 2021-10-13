package com.kedacom.device.core.utils;


import com.alibaba.fastjson.JSON;
import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        remoteRestTemplate.getRestTemplate().postForObject(url, JSON.toJSONString(dto), String.class);
    }

    /**
     * 刻录状态通知
     * @param url  接收方URL
     * @param dto
     */
    public void burnStateNty(String url,DeviceNotifyRequestDTO dto){
        remoteRestTemplate.getRestTemplate().postForObject(url, JSON.toJSONString(dto), String.class);
    }

    /**
     * 异常告警通知
     * @param url  接收方URL
     * @param dto
     */
    public void alarmNty(String url,DeviceNotifyRequestDTO dto){
        remoteRestTemplate.getRestTemplate().postForObject(url, JSON.toJSONString(dto), String.class);
    }


}
