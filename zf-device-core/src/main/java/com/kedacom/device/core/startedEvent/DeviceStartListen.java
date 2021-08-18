package com.kedacom.device.core.startedEvent;

import com.kedacom.core.ConnectorListener;
import com.kedacom.core.ConnectorListenerManager;
import com.kedacom.device.core.utils.McuUrlFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${zf.kmProxy.server_addr}")
    private String kmProxy;

    private final static String OLD_PORT = "45670";

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        factory.setMap();
        if (kmProxy.contains(OLD_PORT)) {
            ConnectorListenerManager.getInstance().register(connectorListener);
        }
    }

}
