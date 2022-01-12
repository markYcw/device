package com.kedacom.device.core.notify.mcu;

import cn.hutool.core.collection.CollectionUtil;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.McuService;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.utils.ContextUtils;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.deviceListener.msgType.MsgType;
import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import com.kedacom.mp.mcu.entity.UmsMcuEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/29 16:56
 * @description mcu 掉线通知
 */
@Slf4j
public class OffLineNotify extends INotify {

    /**
     * cu掉线以后状态置为离线并发送通知给业务
     * @param ssid
     * @param message
     */
    @Override
    public void consumeMessage(Integer ssid, String message) {
        log.info("======================收到掉线通知ssid{}",ssid);
        McuService service = ContextUtils.getBean(McuService.class);
        UmsMcuEntity entity = service.getBySsid(ssid);
        RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
        DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
        //将通知发给业务
        DeviceNotifyRequestDTO notifyRequestDTO = new DeviceNotifyRequestDTO();
        notifyRequestDTO.setMcuId(entity.getId());
        List<KmListenerEntity> list = listenerService.getAll(MsgType.MCU_OFF_LINE.getType());
        if(!CollectionUtil.isEmpty(list)){
            for (KmListenerEntity kmListenerEntity : list) {
                try {
                    CompletableFuture.runAsync(()->notifyUtils.offLineNty(kmListenerEntity.getUrl(),notifyRequestDTO));
                } catch (Exception e) {
                    log.error("==========发送MCU掉线通知给业务时失败{}",e);
                }
            }
        }

    }
}
