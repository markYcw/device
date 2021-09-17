package com.kedacom.device.core.notify.svr.stragegy.notify;

import com.alibaba.fastjson.JSON;
import com.kedacom.device.core.notify.svr.stragegy.INotify;
import com.kedacom.svr.stragegy.svr.pojo.BurnStatus;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:37
 * @description 5.22刻录任务通知
 */
public class BurnTaskStatusNotify extends INotify {
    @Override
    public void consumeMessage(Integer ssid,String message) {
        BurnStatus burnStatus = JSON.parseObject(message, BurnStatus.class);
    }
}
