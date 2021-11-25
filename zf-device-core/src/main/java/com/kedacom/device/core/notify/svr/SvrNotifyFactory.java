package com.kedacom.device.core.notify.svr;

import com.kedacom.device.core.notify.stragegy.DeviceType;

import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.notify.stragegy.NotifyFactory;
import com.kedacom.device.core.utils.ContextUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:30
 * @description 注册SVR设备通知
 */
@Component
public class SvrNotifyFactory {

    public static void  init(){
        NotifyFactory factory = ContextUtils.getBean(NotifyFactory.class);
        factory.register(DeviceType.SVR.getValue(),300,new DeviceStatusNotify());
        factory.register(DeviceType.SVR.getValue(),301,new BurnTaskStatusNotify());
        factory.register(DeviceType.SVR.getValue(),302,new DeviceStatusNotify());
        factory.register(DeviceType.SVR.getValue(),303,new AudioActNotify());
    }

}
