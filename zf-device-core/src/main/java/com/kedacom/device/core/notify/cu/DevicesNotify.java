package com.kedacom.device.core.notify.cu;

import com.alibaba.fastjson.JSON;
import com.kedacom.device.core.notify.cu.loadGroup.CuDeviceLoadThread;
import com.kedacom.device.core.notify.cu.loadGroup.notify.DeviceNotify;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.GetDeviceNotify;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.utils.ContextUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/3 15:06
 * @description
 */
@Slf4j
public class DevicesNotify extends INotify {

    @Override
    protected void consumeMessage(Integer ssid, String message) {
        log.info("========测试加载设备开始");
        DeviceNotify deviceNotify = JSON.parseObject(message, DeviceNotify.class);
        GetDeviceNotify content = deviceNotify.getContent();
        content.setSsid(deviceNotify.getSsid());
        CuDeviceLoadThread cuDeviceLoadThread = ContextUtils.getBean(CuDeviceLoadThread.class);
        cuDeviceLoadThread.onDeviceNotify(content);
        log.info("========测试加载设备结束");
    }
}
