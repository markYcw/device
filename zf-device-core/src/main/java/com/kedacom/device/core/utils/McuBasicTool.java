package com.kedacom.device.core.utils;

import com.kedacom.device.core.entity.McuBasicParam;
import com.kedacom.mp.mcu.entity.UmsMeetingPlatformEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hxj
 * @date: 2021/8/14 14:54
 * @description mcu基本工具类
 */
@Component
public class McuBasicTool {

    @Autowired
    private McuUrlFactory factory;

    public McuBasicParam getParam(UmsMeetingPlatformEntity entity){
        McuBasicParam param = new McuBasicParam();

        String url = factory.geturl(entity.getMcutype());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ssid", entity.getSsid());
        paramMap.put("ssno", System.currentTimeMillis());

        param.setUrl(url);
        param.setParamMap(paramMap);

        return param;
    }
}