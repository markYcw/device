package com.kedacom.device.core.utils;

import cn.hutool.core.util.StrUtil;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.constant.DeviceTypeEnum;
import com.kedacom.device.core.enums.DeviceModelType;
import com.kedacom.device.core.exception.CuException;
import com.kedacom.device.core.exception.MpException;
import com.kedacom.device.core.notify.stragegy.DeviceType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ycw
 * @date: 2021/09/06 15:43
 * @description svr地址工厂
 */
@Component
public class CuUrlFactory {

    @Value("${zf.kmProxy.server_addr}")
    private String kmProxy;

    private final static String Cu_REQUEST_HEAD = "http://";

    //svr2.0版本访问地址
    private final static String Cu_WTO = "/mid/v2/mplat";

    private final static ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

    public void setMap() {
        map.put(DeviceModelType.CU2.getCode(), Cu_REQUEST_HEAD + kmProxy + Cu_WTO);
    }

    public String geturl(Integer type) {
        String url = map.get(type);
        if (StrUtil.isBlank(url)) {
            throw new CuException(DeviceErrorEnum.URL_ERROR);
        }
        return url;
    }
}
