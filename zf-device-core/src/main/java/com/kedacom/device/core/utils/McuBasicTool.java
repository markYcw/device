package com.kedacom.device.core.utils;

import com.kedacom.device.core.basicParam.McuBasicParam;
import com.kedacom.mp.mcu.entity.UmsMcuEntity;
import com.kedacom.util.NumGen;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class McuBasicTool {

    @Autowired
    private McuUrlFactory factory;

    public McuBasicParam getParam(UmsMcuEntity entity) {
        McuBasicParam param = new McuBasicParam();

        String url = factory.geturl(entity.getDevtype());
        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("ssid", Long.valueOf(entity.getSsid()));
//        paramMap.put("ssno", System.currentTimeMillis());
        paramMap.put("ssno", (long) NumGen.getNum());
        log.info("==========此次请求ssid为：{},ssno为：{}",Long.valueOf(entity.getSsid()),(long) NumGen.getNum());
        param.setUrl(url);
        param.setParamMap(paramMap);

        return param;
    }
}
