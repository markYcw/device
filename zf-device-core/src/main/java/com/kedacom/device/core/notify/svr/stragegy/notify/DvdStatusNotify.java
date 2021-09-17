package com.kedacom.device.core.notify.svr.stragegy.notify;

import com.alibaba.fastjson.JSON;
import com.kedacom.device.core.notify.svr.stragegy.INotify;
import com.kedacom.svr.stragegy.svr.pojo.DvdStatus;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:39
 * @description 5.28DVD状态通知
 */
public class DvdStatusNotify extends INotify {
    @Override
    public void consumeMessage(Integer ssid,String message) {
        DvdStatus dvdStatus = JSON.parseObject(message, DvdStatus.class);
    }
}
