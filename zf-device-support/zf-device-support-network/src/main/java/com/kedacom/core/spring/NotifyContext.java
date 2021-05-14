package com.kedacom.core.spring;

import com.kedacom.core.pojo.Notify;
import org.springframework.context.ApplicationContext;

/**
 * @author van.shu
 * @create 2021/5/13 20:31
 */
public class NotifyContext {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext context) {
        this.applicationContext = context;
    }

    /**
     * 发布通知通知
     * @param notify 通知
     */
    public void publishNotify(Notify notify) {

        applicationContext.publishEvent(notify);
    }


}
