package com.kedacom.device.core.notify.svr.stragegy.notify;

import com.alibaba.fastjson.JSON;
import com.kedacom.device.core.notify.svr.stragegy.INotify;
import com.kedacom.svr.stragegy.svr.pojo.SvrDeviceStatus;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:32
 * @description 编解码设备上报通知
 */
public class DeviceStatusNotify extends INotify {
    @Override
    public void consumeMessage(Integer ssid, String message) {
        SvrDeviceStatus svrDeviceStatus = JSON.parseObject(message, SvrDeviceStatus.class);
    }
}
