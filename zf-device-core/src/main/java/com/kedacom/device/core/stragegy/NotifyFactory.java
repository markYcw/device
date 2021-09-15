package com.kedacom.device.core.stragegy;

import com.kedacom.device.devType.DeviceType;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/15 11:04
 * @description 通知工厂 用于返回具体的通知信息处理类
 */
@Component
public class NotifyFactory {

    //存放不同类型的设备通知
    private static HashMap<Integer, HashMap<Integer,INotify>> map = new HashMap<>();


    //初始化通知类
    static {
        //初始化SVR设备上报通知
        HashMap<Integer, INotify> svrStatus = new HashMap<>();
        svrStatus.put(300,new SvrDevStatusNotify());
        map.put(DeviceType.SVR.getValue(),svrStatus);
    }

    public INotify getNotify(Integer devType,Integer notifyType){
        //得到与设备类型对应的通知容器
        HashMap<Integer, INotify> notifyMap = map.get(devType);
        //根据通知类型得到具体的通知类
        INotify iNotify = notifyMap.get(notifyType);
        return iNotify;
    }


}
