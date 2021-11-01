package com.kedacom.device.core.notify.svr;

import com.alibaba.fastjson.JSON;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.svr.stragegy.svr.pojo.AudioAct;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:40
 * @description 5.35语音激励通知
 */
public class AudioActNotify extends INotify {
    @Override
    public void consumeMessage(Integer ssid,String message) {
        AudioAct audioAct = JSON.parseObject(message, AudioAct.class);
    }
}
