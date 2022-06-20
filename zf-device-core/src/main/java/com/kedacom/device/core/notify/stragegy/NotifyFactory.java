package com.kedacom.device.core.notify.stragegy;

import com.kedacom.device.core.notify.cu.CuNotifyFactory;
import com.kedacom.device.core.notify.mcu.McuNotifyFactory;
import com.kedacom.device.core.notify.mt.MtNotifyFactory;
import com.kedacom.device.core.notify.nm.NewMediaNotifyFactory;
import com.kedacom.device.core.notify.svr.SvrNotifyFactory;
import com.kedacom.device.core.notify.vs.VsNotifyFactory;
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

    //用于存放不同类型的设备通知
    private static HashMap<Integer, HashMap<Integer, INotify>> map = new HashMap<>();


    /**
     * 注册通知
     * @param devType 设备类型
     * @param notifyType 消息通知类型
     * @param iNotify 对应的消息处理类
     */
    public void register(Integer devType,Integer notifyType,INotify iNotify){
        HashMap<Integer, INotify> detail = NotifyFactory.map.get(devType);
        if(detail == null){
            HashMap<Integer, INotify> notifyMap = new HashMap<>();
            notifyMap.put(notifyType,iNotify);
            map.put(devType,notifyMap);
        }else {
            detail.put(notifyType,iNotify);
            //map.put(devType,detail);
        }

    }

    /**
     * 初始化各种设备通知
     */
   public static void init(){
       MtNotifyFactory.init();
       SvrNotifyFactory.init();
       CuNotifyFactory.init();
       McuNotifyFactory.init();
       VsNotifyFactory.init();
       NewMediaNotifyFactory.init();
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
