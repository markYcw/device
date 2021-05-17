package com.kedacom.device.ums.notify;

import com.kedacom.device.ums.UmsClient;
import com.kedacom.device.ums.event.DeviceGroupEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/17
 */
@Component
public class UmsNotifyEventListener {

    @Resource
    UmsClient umsClient;

    @EventListener(DeviceGroupEvent.class)
    public void deviceGroupNotify(DeviceGroupEvent deviceGroupEvent) {



    }

}
