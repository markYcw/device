package com.kedacom.device.core.notify.nm;

import com.kedacom.device.core.notify.stragegy.NotifyFactory;
import com.kedacom.device.core.notify.vs.OffLineNotify;
import com.kedacom.device.core.utils.ContextUtils;
import org.springframework.stereotype.Component;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/04/07 14:30
 * @description 注册新媒体设备通知
 */
@Component
public class NewMediaNotifyFactory {

    public static void  init(){
        NotifyFactory factory = ContextUtils.getBean(NotifyFactory.class);
        factory.register(7,400,new NewMediaGroupNotify());
        factory.register(7,401,new NewMediaGroupNotify());
        factory.register(7,403,new NewMediaDeviceStatusNotify());
        factory.register(7,405,new NewMediaSvrBurnNotify());
        factory.register(7,406,new NewMediaSvrAlarmNotify());
    }

}
