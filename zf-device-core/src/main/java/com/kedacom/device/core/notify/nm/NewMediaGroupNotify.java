package com.kedacom.device.core.notify.nm;
import com.alibaba.fastjson.JSON;
import com.kedacom.device.core.notify.nm.notify.PreGroupsNotify;
import com.kedacom.device.core.notify.nm.pojo.NmGetGroupNotify;
import com.kedacom.device.core.notify.stragegy.INotify;


/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/3 15:06
 * @description
 */
public class NewMediaGroupNotify extends INotify {

    @Override
    protected void consumeMessage(Integer ssid, String message) {
        PreGroupsNotify getGroupNotify = JSON.parseObject(message, PreGroupsNotify.class);
        NmGetGroupNotify content = getGroupNotify.getContent();
        NewMediaDeviceLoadThread.getInstance().onDeviceGroupNotify(content);
    }
}
