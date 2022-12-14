package com.kedacom.device.core.utils;

import cn.hutool.core.util.StrUtil;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.constant.DeviceTypeEnum;
import com.kedacom.device.core.enums.DeviceModelType;
import com.kedacom.device.core.exception.MpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hxj
 * @date: 2021/8/13 15:43
 * @description mcu地址工厂
 */
@Component
public class McuUrlFactory {

    @Value("${zf.kmProxy.server_addr}")
    private String kmProxy;

    private final static String MCU_REQUEST_HEAD = "http://";

    private final static String MCU_FIVE = "/mid/v2/mcu";

    private final static ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

    public void setMap() {
        map.put(DeviceModelType.MCU5.getCode(), MCU_REQUEST_HEAD + kmProxy + MCU_FIVE);
    }

    public String geturl(Integer type) {
        String url = map.get(type);
        if (StrUtil.isBlank(url)) {
            throw new MpException(DeviceErrorEnum.URL_ERROR);
        }
        return url;
    }
}
