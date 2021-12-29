package com.kedacom.device.core.notify.stragegy;

import com.kedacom.device.core.utils.ContextUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/15 10:00
 * @description 信息处理类 根据通知返回类型处理具体的通知逻辑
 */
@Data
@Slf4j
public class NotifyHandler {

    private static volatile NotifyHandler instance;

    private NotifyHandler(){}

    public static NotifyHandler getInstance(){
        if (null == instance){
            synchronized (NotifyHandler.class){
                if(null == instance){
                    instance = new NotifyHandler();
                }
            }
        }
        return instance;
    }

    /**
     * 根据设备类型和通知类型得到具体的消息处理类处理消息
     * @param ssid 会话ID
     * @param devType 设备类型
     * @param notifyType 通知类型
     * @param message 消息
     */
    public void distributeMessages(Integer ssid,Integer devType, Integer notifyType, String message){
        INotify notify = ContextUtils.getBean(NotifyFactory.class).getNotify(devType, notifyType);
        notify.consumeMessage(ssid,message);
    }

}
