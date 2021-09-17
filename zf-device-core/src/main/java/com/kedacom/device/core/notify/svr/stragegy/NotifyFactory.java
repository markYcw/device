package com.kedacom.device.core.notify.svr.stragegy;

import com.kedacom.device.core.notify.svr.stragegy.notify.SvrNotifyFactory;
import com.kedacom.device.core.utils.ContextUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/15 11:04
 * @description 通知工厂 用于返回具体的通知信息处理类
 */
public class NotifyFactory {


    //用于存放不同类型的设备通知
    private static HashMap<Integer, HashMap<Integer, INotify>> map = new HashMap<>();


    /**
     * 注册通知
     * @param devType 设备类型
     * @param notifyType 通知类型
     * @param iNotify 具体的消费通知类 注：iNotify为消费通知类的超类，这里需要注意的是，在这需要注册的是具体的消费通知类也就是其子类
     */
    public void register(Integer devType, Integer notifyType, INotify iNotify){
        HashMap<Integer, INotify> map = NotifyFactory.map.get(devType);
        if(map == null){
            HashMap<Integer, INotify> m = new HashMap<>();
            m.put(notifyType,iNotify);
        }
    }

    /**
     * 初始化各种设备通知
     */
   private static void init(){
       ContextUtils.getBean(SvrNotifyFactory.class).init();

   }

    static {
       init();
    }

    /**
     * 根据设备类型和通知类型获取具体的消费通知类
     * @param devType
     * @param notifyType
     * @return
     */
    public INotify getNotify(Integer devType, Integer notifyType){
        //得到与设备类型对应的通知容器
        HashMap<Integer, INotify> notifyMap = map.get(devType);
        //根据通知类型得到具体的通知类
        INotify iNotify = notifyMap.get(notifyType);
        return iNotify;
    }


}
