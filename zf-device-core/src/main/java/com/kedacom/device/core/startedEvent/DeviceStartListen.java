package com.kedacom.device.core.startedEvent;

import com.kedacom.core.ConnectorListener;
import com.kedacom.core.ConnectorListenerManager;
import com.kedacom.device.core.utils.CuUrlFactory;
import com.kedacom.device.core.utils.McuUrlFactory;
import com.kedacom.device.core.utils.SvrUrlFactory;
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

    @Autowired
    private SvrUrlFactory svrUrlFactory;

    @Autowired
    private CuUrlFactory cuUrlFactory;

    @Value("${zf.kmProxy.server_addr}")
    private String kmProxy;

    private final static String DEVICE_PORT = "45670";

    private final static String MCU_PORT = "13000";

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

        //mcu访问地址初始化
        factory.setMap();
        //svr访问地址初始化
        svrUrlFactory.setMap();
        //cu访问地址初始化
        cuUrlFactory.setMap();

        if (kmProxy.contains(DEVICE_PORT)) {
            ConnectorListenerManager.getInstance().register(connectorListener);
        }
    }

}
