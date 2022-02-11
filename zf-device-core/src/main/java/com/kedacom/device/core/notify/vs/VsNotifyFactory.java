package com.kedacom.device.core.notify.vs;

import com.kedacom.device.core.notify.stragegy.NotifyFactory;
import com.kedacom.device.core.notify.svr.BurnTaskStatusNotify;
import com.kedacom.device.core.notify.svr.DeviceStatusNotify;
import com.kedacom.device.core.notify.svr.DvdStatusNotify;
import com.kedacom.device.core.notify.svr.OffLineNotify;
import com.kedacom.device.core.utils.ContextUtils;
import org.springframework.stereotype.Component;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:30
 * @description 注册SVR设备通知
 */
@Component
public class VsNotifyFactory {

    public static void  init(){
        NotifyFactory factory = ContextUtils.getBean(NotifyFactory.class);
        factory.register(11,1,new OffLineNotify());
    }

}
