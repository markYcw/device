package com.kedacom.device.core.notify.nm;
import com.alibaba.fastjson.JSON;
import com.kedacom.device.core.notify.nm.NewMediaDeviceLoadThread;
import com.kedacom.device.core.notify.nm.notify.PreGroupsModifyNotify;
import com.kedacom.device.core.notify.nm.notify.PreGroupsNotify;
import com.kedacom.device.core.notify.nm.pojo.NmGetGroupNotify;
import com.kedacom.device.core.notify.nm.pojo.NmGroupModifyNotify;
import com.kedacom.device.core.notify.stragegy.INotify;


/**
 * @author ycw
 * @version v1.0
 * @date 2022/04/08 15:06
 * @description 分组变更通知
 */
public class NewMediaGroupModifyNotify extends INotify {

    @Override
    protected void consumeMessage(Integer ssid, String message) {
        PreGroupsModifyNotify getGroupNotify = JSON.parseObject(message, PreGroupsModifyNotify.class);
        NmGroupModifyNotify content = getGroupNotify.getContent();
        NewMediaDeviceLoadThread.getInstance().onGroupChangeNotify(content);
    }
}
