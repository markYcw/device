package com.kedacom.device.core.notify.svr;

import com.kedacom.device.core.notify.stragegy.DeviceType;

import com.kedacom.device.core.notify.stragegy.NotifyFactory;
import com.kedacom.device.core.utils.ContextUtils;
import org.springframework.stereotype.Component;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:30
 * @description
 */
@Component
public class SvrNotifyFactory {

    public void init(){
        NotifyFactory factory = ContextUtils.getBean(NotifyFactory.class);
        factory.register(DeviceType.SVR.getValue(),300,new DeviceStatusNotify());
        factory.register(DeviceType.SVR.getValue(),301,new BurnTaskStatusNotify());
        factory.register(DeviceType.SVR.getValue(),302,new DeviceStatusNotify());
        factory.register(DeviceType.SVR.getValue(),303,new AudioActNotify());
    }

}
