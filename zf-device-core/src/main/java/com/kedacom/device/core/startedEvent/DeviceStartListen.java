package com.kedacom.device.core.startedEvent;

import com.kedacom.core.ConnectorListener;
import com.kedacom.core.ConnectorListenerManager;
import com.kedacom.device.core.notify.stragegy.NotifyFactory;
import com.kedacom.device.core.service.CuService;
import com.kedacom.device.core.service.DataService;
import com.kedacom.device.core.service.SvrService;
import com.kedacom.device.core.service.NewMediaService;
import com.kedacom.device.core.task.ControlPowerStatusCallback;
import com.kedacom.device.core.utils.ContextUtils;
import com.kedacom.device.core.utils.CuUrlFactory;
import com.kedacom.device.core.utils.McuUrlFactory;
import com.kedacom.power.ControlPower;
import com.kedacom.power.entity.PowerConfigEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hexijian
 * @date 2021/5/20 09:51
 */
@Slf4j
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
    private NewMediaService newMediaService;

    @Autowired
    private ControlPowerService controlPowerService;

    @Value("${zf.kmProxy.server_addr}")
    private String kmProxy;

    @Value("${zf.power.tcp:0}")
    private String powerTcp;

    private final static String DEVICE_PORT = "45670";

    private final static String MCU_PORT = "13000";

    private final static String POWER_TCP_START = "1";

    static Retryer<Boolean> retryer;

    static {
        retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(result -> Objects.equals(false, result))
                .retryIfExceptionOfType(Exception.class)
                .withWaitStrategy(WaitStrategies.fixedWait(2, TimeUnit.MINUTES))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {


        //初始化通知工厂
        NotifyFactory.init();
        //mcu访问地址初始化
        factory.setMap();
        //cu访问地址初始化
        cuUrlFactory.setMap();
        //服务重启时重启监控平台
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(8);
        poolExecutor.schedule(() -> dataService.dcOne(), 1, TimeUnit.SECONDS);
        poolExecutor.schedule(() -> cuService.logoutCu(), 1, TimeUnit.SECONDS);
        poolExecutor.schedule(() -> cuService.initCu(), 2, TimeUnit.MINUTES);
        poolExecutor.schedule(() -> newMediaService.logoutById(1), 1, TimeUnit.SECONDS);
        poolExecutor.schedule(() -> newMediaService.initNM(), 1, TimeUnit.MINUTES);
        //执行svr数据迁移功能
        poolExecutor.scheduleAtFixedRate(() -> svrService.synData(), 1, 60, TimeUnit.SECONDS);

        if (kmProxy.contains(DEVICE_PORT)) {
            ConnectorListenerManager.getInstance().register(connectorListener);
        }

        // 启动电源的tcp连接，延迟是为了方便power-server能知晓电源状态变更后调用device-server的借口
        if (Objects.equals(POWER_TCP_START, powerTcp)) {
            poolExecutor.schedule(this::startPowerTcpServer, 2, TimeUnit.MINUTES);
        }
    }

    public void startPowerTcpServer() {
        try {
            PowerConfigMapper powerConfigMapper = ContextUtils.getBean(PowerConfigMapper.class);
            ControlPowerService controlPowerService = ContextUtils.getBean(ControlPowerService.class);
            PowerConfigEntity powerConfigEntity = powerConfigMapper.selectOne(new LambdaQueryWrapper<>());
            retryer.call(() -> {
                log.info("B_WANT电源配置启动TCP端口号为：{}", powerConfigEntity.getPort());
                ControlPower.getInstance().stopServer();
                controlPowerService.changeBwantAllDeviceStatusDown();
                ControlPower.getInstance().startServer(powerConfigEntity.getPort(), new ControlPowerStatusCallback());
                return true;
            });
        } catch (Exception e) {
            log.error("开启B_WANT，tcp连接端口最终失败，case：" + e, e);
        }
    }
}
