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
 * @description
 */
@Component
public class SvrNotifyFactory {

    public static void  init(){
        NotifyFactory factory = ContextUtils.getBean(NotifyFactory.class);
        HashMap<Integer, INotify> svrMap = new HashMap<>();
        svrMap.put(300,new DeviceStatusNotify());
        svrMap.put(301,new BurnTaskStatusNotify());
        svrMap.put(302,new DeviceStatusNotify());
        svrMap.put(303,new AudioActNotify());
        factory.register(DeviceType.SVR.getValue(),svrMap);
    }

}
