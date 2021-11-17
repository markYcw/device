package com.kedacom.device.core.notify.cu;

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
public class CuNotifyFactory {

    public static void init(){
        NotifyFactory factory = ContextUtils.getBean(NotifyFactory.class);
        factory.register(DeviceType.CU2.getValue(),1,new OffLineNotify());
        factory.register(DeviceType.CU2.getValue(),500,new GroupNotify());
        factory.register(DeviceType.CU2.getValue(),501,new DevicesNotify());
        factory.register(DeviceType.CU2.getValue(),502,new DevicesStatusSubscribeNotify());
    }

}
