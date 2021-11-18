package com.kedacom.device.core.utils;

import cn.hutool.core.util.StrUtil;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.constant.DeviceTypeEnum;
import com.kedacom.device.core.exception.MpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ycw
 * @date: 2021/09/06 15:43
 * @description svr地址工厂
 */
@Component
public class SvrUrlFactory{

    @Value("${zf.kmProxy.server_addr}")
    private String kmProxy;

    private final static String SVR_REQUEST_HEAD = "http://";

    //svr2.0版本访问地址
    private final static String SVR_WTO = "/mid/v2/svr";

    private final static ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

    public void setMap() {
        map.put(DeviceTypeEnum.SVR.getCode(), SVR_REQUEST_HEAD + kmProxy + SVR_WTO);
    }

    public String geturl(Integer type) {
        String url = map.get(type);
        if (StrUtil.isBlank(url)) {
            throw new MpException(DeviceErrorEnum.URL_ERROR);
        }
        return url;
    }
}
