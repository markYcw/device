package com.kedacom.device.core.notify.vs;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.service.VrsFiveService;
import com.kedacom.device.core.utils.ContextUtils;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.deviceListener.msgType.MsgType;
import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import com.kedacom.vs.entity.VsEntity;
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
        VrsFiveService service = ContextUtils.getBean(VrsFiveService.class);
        VsEntity entity = service.getBySsid(ssid);
        if(ObjectUtil.isNotNull(entity)){
            service.logoutSsid(entity.getId());
            RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
            DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
            //将通知发给业务
            DeviceNotifyRequestDTO notifyRequestDTO = new DeviceNotifyRequestDTO();
            notifyRequestDTO.setDbId(entity.getId());
            notifyRequestDTO.setMsgType(MsgType.VS_OFF_LINE.getType());
            List<KmListenerEntity> list = listenerService.getAll(MsgType.VS_OFF_LINE.getType());
            if(!CollectionUtil.isEmpty(list)){
                for (KmListenerEntity kmListenerEntity : list) {
                    try {
                        CompletableFuture.runAsync(()->notifyUtils.offLineNty(kmListenerEntity.getUrl(),notifyRequestDTO));
                    } catch (Exception e) {
                        log.error("==========发送vs掉线通知给业务时失败{}",e);
                    }
                }
            }
        }

    }
}
