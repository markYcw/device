package com.kedacom.device.core.startedEvent;

import com.kedacom.core.ConnectorListener;
import com.kedacom.core.ConnectorListenerManager;
import com.kedacom.device.core.utils.McuUrlFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Auther: hxj
 * @Date: 2021/5/20 09:51
 */
@Component
public class DeviceStartListen implements ApplicationListener<ApplicationStartedEvent> {

    @Resource
    private ConnectorListener connectorListener;

    @Autowired
    private McuUrlFactory factory;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        factory.setMap();
        ConnectorListenerManager.getInstance().register(connectorListener);
    }

}
