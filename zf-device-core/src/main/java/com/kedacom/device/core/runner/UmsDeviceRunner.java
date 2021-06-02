package com.kedacom.device.core.runner;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kedacom.device.core.constant.DeviceConstants;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.entity.UmsPair;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.service.DeviceManagerService;
import com.kedacom.device.core.service.UmsManagerService;
import com.kedacom.device.core.task.UmsDeviceTask;
import com.kedacom.util.ThreadPoolUtil;
import com.kedacom.ums.requestdto.UmsDeviceInfoSyncRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * @author van.shu
 * @create 2020/8/26 16:30
 */
@Component
@Slf4j
public class UmsDeviceRunner /*implements CommandLineRunner*/ {

    /**
     * 去掉定时同步任务
     */

    private Map<String, UmsPair<ScheduledExecutorService, ScheduledFuture>> executorServiceMap = new ConcurrentHashMap<>();


    private int coreSize = 1;


    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceManagerService deviceManagerService;


    public void notifyUmsChange(DeviceInfoEntity deviceInfoEntity, Integer umsChangeType) {

        if (DeviceConstants.ADD_UMS.equals(umsChangeType)) {
            createScheduledThreadPoolExecutor(deviceInfoEntity);
        } else if (DeviceConstants.DEL_UMS.equals(umsChangeType)) {
            removeScheduledThreadPoolExecutorAndTask(deviceInfoEntity.getId());
        }

    }

    //@Override
    public void run(String... args) throws Exception {


        //每个UMS平台一个线程
        QueryWrapper<DeviceInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DeviceInfoEntity::getDeviceType, "UMS");

        List<DeviceInfoEntity> deviceInfoEntityList = deviceMapper.selectList(queryWrapper);

        if (CollUtil.isEmpty(deviceInfoEntityList)) {
            log.warn("当前系统未配置统一平台设备....");
            return;
        }

        for (DeviceInfoEntity deviceInfoEntity : deviceInfoEntityList) {

            createScheduledThreadPoolExecutor(deviceInfoEntity);

        }

    }

    private void createScheduledThreadPoolExecutor(DeviceInfoEntity deviceInfoEntity) {

        Assert.notNull(deviceInfoEntity,"UMS平台不能为空");

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(coreSize, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("[UMS-period-" + deviceInfoEntity.getName() + "]");
            return t;
        });

        ScheduledFuture<?> scheduledFuture = executorService.scheduleWithFixedDelay(new UmsDeviceTask(deviceInfoEntity.getId()), 5, 30, TimeUnit.MINUTES);

        UmsPair<ScheduledExecutorService, ScheduledFuture> umsPair = new UmsPair<>(executorService, scheduledFuture);

        executorServiceMap.put(deviceInfoEntity.getId(), umsPair);

        log.info("平台添加定时任务成功，平台Id:[{}]", deviceInfoEntity.getId());

    }

    private void removeScheduledThreadPoolExecutorAndTask(String umsDeviceId) {

        Assert.notNull(umsDeviceId, "UMS平台Id不能为空");

        UmsPair<ScheduledExecutorService, ScheduledFuture> umsPair = executorServiceMap.get(umsDeviceId);

        if (umsPair == null) {
            log.warn("当前平台未有UMS-period定时任务，平台Id:[{}]", umsDeviceId);
            return;
        }

        ScheduledFuture scheduledFuture = umsPair.getV();

        ScheduledExecutorService scheduledExecutorService = umsPair.getK();

        scheduledFuture.cancel(true);

        scheduledExecutorService.shutdown();

        if (scheduledExecutorService.isShutdown()) {
            log.warn("移除当前平台UMS-period定时任务成功，平台Id:[{}]", umsDeviceId);
        }

    }

    public void syncDeviceInfo(DeviceInfoEntity deviceInfoEntity) {

        UmsDeviceInfoSyncRequestDto request = new UmsDeviceInfoSyncRequestDto();
        request.setUmsId(deviceInfoEntity.getId());
        ThreadPoolUtil.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                deviceManagerService.syncDeviceData(request);
            }
        });

    }


}
