package com.kedacom.device.core.notify.mt;

import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.MtService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author wangxy
 * @describe 终端通知
 * @date 2022/1/18
 */
@Slf4j
public class MtNotifyHandle extends INotify {

    @Resource
    MtService mtService;

    @Override
    protected void consumeMessage(Integer ssid, String message) {

        mtService.mtNotify(message);

    }

}
