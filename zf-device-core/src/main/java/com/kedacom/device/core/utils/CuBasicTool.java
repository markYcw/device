package com.kedacom.device.core.utils;

import com.kedacom.cu.entity.CuEntity;
import com.kedacom.device.core.basicParam.CuBasicParam;
import com.kedacom.device.core.basicParam.SvrBasicParam;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.util.NumGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ycw
 * @date: 2021/09/06 14:54
 * @description cu基本工具类
 */
@Component
@Slf4j
public class CuBasicTool {

    @Autowired
    private CuUrlFactory factory;

    public CuBasicParam getParam(CuEntity entity) {
        CuBasicParam param = new CuBasicParam();

        String url = factory.geturl(entity.getType());
        Map<String, Long> paramMap = new HashMap<>();
        Long s = Long.valueOf(entity.getSsid());
        Long n = (long) NumGen.getNum();
        paramMap.put("ssid",s);
        paramMap.put("ssno",n);

        log.info("==========此次请求ssid为：{},ssno为：{}",s,n);
        param.setUrl(url);
        param.setParamMap(paramMap);

        return param;
    }
}
