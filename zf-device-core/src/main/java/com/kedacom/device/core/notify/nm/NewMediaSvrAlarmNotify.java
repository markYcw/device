package com.kedacom.device.core.notify.nm;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.kedacom.api.WebsocketFeign;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.notify.nm.notify.PreGroupsNotify;
import com.kedacom.device.core.notify.nm.notify.SvrAlarmNotify;
import com.kedacom.device.core.notify.nm.notify.SvrBurnNotify;
import com.kedacom.device.core.notify.nm.pojo.*;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.utils.ContextUtils;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.deviceListener.msgType.MsgType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/3 15:06
 * @description
 */
@Slf4j
public class NewMediaSvrAlarmNotify extends INotify {

    @Override
    protected void consumeMessage(Integer ssid, String message) {
        SvrAlarmNotify alarmNotify = JSON.parseObject(message, SvrAlarmNotify.class);
        NmSvrAlarmNotify content = alarmNotify.getContent();
        AlarmInfo alarmInfo = content.getAlarmInfo();
        RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
        DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
        WebsocketFeign ws = ContextUtils.getBean(WebsocketFeign.class);
        log.info("===============发送新媒体SVR告警通知webSocket给前端{}", JSON.toJSONString(message));
        ws.sendInfo(JSON.toJSONString(alarmInfo));
        alarmInfo.setMsgType(MsgType.NM_SVR_ALARM_NTY.getType());
        alarmInfo.setDbId(1);
        List<KmListenerEntity> list = listenerService.getAll(MsgType.NM_SVR_ALARM_NTY.getType());
        if(!CollectionUtil.isEmpty(list)){
            for (KmListenerEntity kmListenerEntity : list) {
                notifyUtils.offLineNty(kmListenerEntity.getUrl(),alarmInfo);
            }
        }
    }
}
