package com.kedacom.device.core.notify.svr.stragegy;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/15 11:07
 * @description 通知信息处理超类
 */
public abstract class INotify {

    public abstract void consumeMessage(Integer ssid,String message);

}
