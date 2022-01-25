package com.kedacom.device.core.notify.svr;

import com.kedacom.device.core.notify.stragegy.NotifyFactory;
import com.kedacom.device.core.utils.ContextUtils;
import org.springframework.stereotype.Component;

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
        factory.register(5,300,new DeviceStatusNotify());
        factory.register(5,301,new BurnTaskStatusNotify());
        factory.register(5,302,new DvdStatusNotify());
        //factory.register(5,303,new AudioActNotify());
        factory.register(5,1,new OffLineNotify());
        factory.register(6,300,new DeviceStatusNotify());
        factory.register(6,301,new BurnTaskStatusNotify());
        factory.register(6,302,new DvdStatusNotify());
        //factory.register(6,303,new AudioActNotify());
        factory.register(6,1,new OffLineNotify());
    }

}
