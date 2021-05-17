package com.kedacom.core.spring;

import com.kedacom.core.pojo.Notify;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author van.shu
 * @create 2021/5/13 20:31
 */
public class NotifyContext {

    public NotifyContext() {

    }

    private ApplicationContext applicationContext;

    private Map<String, Class<?>> notifyMap = new HashMap<>();

    public void setApplicationContext(ApplicationContext context) {
        this.applicationContext = context;
    }

    public void setNotifyMap(Map<String, Class<?>> map) {
        this.notifyMap = map;
    }

    public  Map<String, Class<?>> getNotifyMap() {
        return notifyMap;
    }

    /**
     * 发布通知通知
     * @param notify 通知
     */
    public void publishNotify(Notify notify) {

        applicationContext.publishEvent(notify);
    }


}
