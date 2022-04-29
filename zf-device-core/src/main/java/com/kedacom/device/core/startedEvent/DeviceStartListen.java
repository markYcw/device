package com.kedacom.device.core.startedEvent;

import com.kedacom.core.ConnectorListener;
import com.kedacom.core.ConnectorListenerManager;
import com.kedacom.device.core.notify.stragegy.NotifyFactory;
import com.kedacom.device.core.service.CuService;
import com.kedacom.device.core.service.DataService;
import com.kedacom.device.core.service.SvrService;
import com.kedacom.device.core.utils.CuUrlFactory;
import com.kedacom.device.core.utils.McuUrlFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    private CuUrlFactory cuUrlFactory;

    @Autowired
    private CuService cuService;

    @Autowired
    private SvrService svrService;

    @Autowired
    private DataService dataService;

    @Value("${zf.kmProxy.server_addr}")
    private String kmProxy;

    private final static String DEVICE_PORT = "45670";

    private final static String MCU_PORT = "13000";

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {


        //初始化通知工厂
        NotifyFactory.init();
        //mcu访问地址初始化
        factory.setMap();
        //cu访问地址初始化
        cuUrlFactory.setMap();
        //服务重启时重启监控平台
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(6);
        poolExecutor.schedule(()->dataService.dcOne(),1,TimeUnit.SECONDS);
        poolExecutor.schedule(()->cuService.logoutCu(),1,TimeUnit.SECONDS);
        poolExecutor.schedule(()->cuService.initCu(),2, TimeUnit.MINUTES);
        //执行svr数据迁移功能
        poolExecutor.scheduleAtFixedRate(()->svrService.synData(),1,60,TimeUnit.SECONDS);

        if (kmProxy.contains(DEVICE_PORT)) {
            ConnectorListenerManager.getInstance().register(connectorListener);
        }
    }

}
