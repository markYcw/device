package com.kedacom.device.core.notify.cu;

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
 * @description
 */
@Component
public class CuNotifyFactory {

    public static void init(){
        NotifyFactory factory = ContextUtils.getBean(NotifyFactory.class);
        HashMap<Integer, INotify> cuMap = new HashMap<>();
        cuMap.put(1,new OffLineNotify());
        cuMap.put(500,new GroupNotify());
        factory.register(DeviceType.CU2.getValue(), cuMap);
    }

}
