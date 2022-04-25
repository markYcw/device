package com.kedacom.device.core.notify.nm;
import com.alibaba.fastjson.JSON;
import com.kedacom.device.core.notify.nm.notify.PreDeviceStatusNotify;
import com.kedacom.device.core.notify.nm.notify.PreGroupsNotify;
import com.kedacom.device.core.notify.nm.pojo.NmDeviceStatusNotify;
import com.kedacom.device.core.notify.nm.pojo.NmGetGroupNotify;
import com.kedacom.device.core.notify.stragegy.INotify;


/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/3 15:06
 * @description
 */
public class NewMediaDeviceStatusNotify extends INotify {

    @Override
    protected void consumeMessage(Integer ssid, String message) {
        PreDeviceStatusNotify preDeviceStatusNotify = JSON.parseObject(message, PreDeviceStatusNotify.class);
        NmDeviceStatusNotify content = preDeviceStatusNotify.getContent();
        NewMediaDeviceLoadThread.getInstance().onDeviceStatus(content);
    }
}
