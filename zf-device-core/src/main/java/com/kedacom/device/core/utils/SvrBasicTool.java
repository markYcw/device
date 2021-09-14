package com.kedacom.device.core.utils;

import com.kedacom.device.core.entity.SvrBasicParam;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.util.NumGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ycw
 * @date: 2021/09/06 14:54
 * @description svr基本工具类
 */
@Component
public class SvrBasicTool {

    @Autowired
    private SvrUrlFactory factory;

    public SvrBasicParam getParam(SvrEntity entity) {
        SvrBasicParam param = new SvrBasicParam();

        String url = factory.geturl(entity.getDevType());
        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("ssid", Long.valueOf(entity.getSsid()));
        paramMap.put("ssno", (long) NumGen.getNum());

        param.setUrl(url);
        param.setParamMap(paramMap);

        return param;
    }
}
