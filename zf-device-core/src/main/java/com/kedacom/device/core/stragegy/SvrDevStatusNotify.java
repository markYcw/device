package com.kedacom.device.core.stragegy;


import com.alibaba.fastjson.JSON;
import com.kedacom.device.core.notify.svr.pojo.SvrDeviceStatus;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/15 15:42
 * @description 设备上报通知
 */
public class SvrDevStatusNotify extends INotify{
    @Override
    public void consumeMessage(String message) {
        SvrDeviceStatus svrDeviceStatus = JSON.parseObject(message, SvrDeviceStatus.class);
        //Todo... 1是否通过HTTP发通知给业务 2或者是通过回调的方式把信息发给业务 这里涉及的问题就是我们要把这个通知里面的svrDeviceStatus这个类要暴露给业务这样他们不用自己在定义那个类了。。。

    }
}
