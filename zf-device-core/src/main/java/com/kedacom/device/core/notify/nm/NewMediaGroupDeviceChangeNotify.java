package com.kedacom.device.core.notify.nm;
import com.alibaba.fastjson.JSON;
import com.kedacom.device.core.notify.nm.NewMediaDeviceLoadThread;
import com.kedacom.device.core.notify.nm.notify.PreGroupsDeviceChangeNotify;
import com.kedacom.device.core.notify.nm.notify.PreGroupsModifyNotify;
import com.kedacom.device.core.notify.nm.pojo.NmGroupDeviceChangeNotify;
import com.kedacom.device.core.notify.nm.pojo.NmGroupModifyNotify;
import com.kedacom.device.core.notify.stragegy.INotify;


/**
 * @author ycw
 * @version v1.0
 * @date 2022/04/08 15:06
 * @description 设备和分组关系变更通知
 */
public class NewMediaGroupDeviceChangeNotify extends INotify {

    @Override
    protected void consumeMessage(Integer ssid, String message) {
        PreGroupsDeviceChangeNotify getGroupNotify = JSON.parseObject(message, PreGroupsDeviceChangeNotify.class);
        NmGroupDeviceChangeNotify content = getGroupNotify.getContent();
        NewMediaDeviceLoadThread.getInstance().onDeviceGroupChangeNotify(content);
    }
}
