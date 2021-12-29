package com.kedacom.device.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kedacom.device.core.notify.stragegy.NotifyHandler;
import com.kedacom.device.core.service.DeviceNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/12/29 9:54
 * @description
 */
@Slf4j
@Service
public class DeviceNotifyServiceImpl implements DeviceNotifyService {


    @Override
    public void handleNotify(String notify) {
        log.info("收到设备通知:{}", notify);
        JSONObject jsonObject = JSONObject.parseObject(notify);
        Integer type = (Integer) jsonObject.get("type");
        Integer ssid = (Integer) jsonObject.get("ssid");
        Integer devType = (Integer) jsonObject.get("devType");
         if(ssid !=null){
             //根据设备类型和通知类型分发通知
            CompletableFuture.runAsync(()-> NotifyHandler.getInstance().distributeMessages(ssid,devType,type,notify));
        }
    }
}
