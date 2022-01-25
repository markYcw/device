package com.kedacom.device.core.notify.svr;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.kedacom.api.WebsocketFeign;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.service.SvrService;
import com.kedacom.device.core.utils.ContextUtils;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.deviceListener.msgType.MsgType;
import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import com.kedacom.pojo.SystemWebSocketMessage;
import com.kedacom.svr.entity.SvrEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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
        WebsocketFeign websocketFeign = ContextUtils.getBean(WebsocketFeign.class);
        SvrService service = ContextUtils.getBean(SvrService.class);
        SvrEntity entity = service.getBySsid(ssid);
        if(ObjectUtil.isNotNull(entity)){
            service.logoutById(entity.getId());
            RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
            DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
            SystemWebSocketMessage msg = new SystemWebSocketMessage();
            msg.setOperationType(10);
            msg.setServerName("device");
            msg.setData("IP为："+entity.getIp()+"SVR掉线通知");
            websocketFeign.sendInfo(JSON.toJSONString(msg));
            log.info("===========发送SVR掉线通知给前端{}",JSON.toJSONString(msg));
            //将通知发给业务
            DeviceNotifyRequestDTO notifyRequestDTO = new DeviceNotifyRequestDTO();
            notifyRequestDTO.setDbId(entity.getId());
            notifyRequestDTO.setMsgType(MsgType.SVR_OFF_LINE.getType());
            List<KmListenerEntity> list = listenerService.getAll(MsgType.SVR_OFF_LINE.getType());
            if(!CollectionUtil.isEmpty(list)){
                for (KmListenerEntity kmListenerEntity : list) {
                    try {
                        CompletableFuture.runAsync(()->notifyUtils.offLineNty(kmListenerEntity.getUrl(),notifyRequestDTO));
                    } catch (Exception e) {
                        log.error("==========发送SVR掉线通知给业务时失败{}",e);
                    }
                }
            }
        }

    }
}
