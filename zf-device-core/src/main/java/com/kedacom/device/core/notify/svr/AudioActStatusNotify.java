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
import com.kedacom.pojo.SystemWebSocketMessage;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.stragegy.svr.pojo.AudioAct;
import com.kedacom.svr.stragegy.svr.pojo.AudioActStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:40
 * @description 5.35语音激励通知
 */
@Slf4j
public class AudioActStatusNotify extends INotify {
    @Override
    public void consumeMessage(Integer ssid,String message) {
        WebsocketFeign websocketFeign = ContextUtils.getBean(WebsocketFeign.class);
        AudioActStatus status = JSON.parseObject(message, AudioActStatus.class);
        SvrService service = ContextUtils.getBean(SvrService.class);
        RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
        DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
        SvrEntity entity = service.getBySsid(ssid);
        if(ObjectUtil.isNotNull(entity)){
            //发送webSocket给前端
            SystemWebSocketMessage msg = new SystemWebSocketMessage();
            msg.setOperationType(9);
            msg.setServerName("device");
            msg.setData(status);
            websocketFeign.sendInfo(JSON.toJSONString(msg));
            log.info("===============发送语音激励状态通知webSocket给前端{}", JSON.toJSONString(msg));
            //将通知发给业务
            status.setMsgType(MsgType.SVR_AUDIO_ACT_STATE_NTY.getType());
            status.setDbId(entity.getId());
            List<KmListenerEntity> list = listenerService.getAll(MsgType.SVR_AUDIO_ACT_STATE_NTY.getType());
            if(!CollectionUtil.isEmpty(list)){
                for (KmListenerEntity kmListenerEntity : list) {
                    notifyUtils.offLineNty(kmListenerEntity.getUrl(),status);
                }
            }
        }

    }
}
