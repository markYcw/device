package com.kedacom.device.core.notify.mcu;

import com.kedacom.device.core.notify.stragegy.DeviceType;
import com.kedacom.device.core.notify.stragegy.NotifyFactory;
import com.kedacom.device.core.utils.ContextUtils;
import org.springframework.stereotype.Component;


/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:30
 * @description 注册监控平台设备通知
 */
@Component
public class McuNotifyFactory {

    public static void init(){
        NotifyFactory factory = ContextUtils.getBean(NotifyFactory.class);
        factory.register(DeviceType.MCU5.getValue(),1,new OffLineNotify());
    }

}
