package com.kedacom.device.core.notify.nm;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.kedacom.api.WebsocketFeign;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.notify.nm.notify.PreGroupsNotify;
import com.kedacom.device.core.notify.nm.notify.SvrBurnNotify;
import com.kedacom.device.core.notify.nm.pojo.BurnInfo;
import com.kedacom.device.core.notify.nm.pojo.NmGetGroupNotify;
import com.kedacom.device.core.notify.nm.pojo.NmSvrBurnNotify;
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
public class NewMediaSvrBurnNotify extends INotify {

    @Override
    protected void consumeMessage(Integer ssid, String message) {
        SvrBurnNotify getGroupNotify = JSON.parseObject(message, SvrBurnNotify.class);
        NmSvrBurnNotify content = getGroupNotify.getContent();
        BurnInfo burnInfo = content.getBurnInfo();
        RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
        DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
        WebsocketFeign ws = ContextUtils.getBean(WebsocketFeign.class);
        log.info("===============发送新媒体SVR刻录状态通知webSocket给前端{}", JSON.toJSONString(message));
        ws.sendInfo(JSON.toJSONString(burnInfo));
        burnInfo.setMsgType(MsgType.NM_SVR_BURN_STATE.getType());
        burnInfo.setDbId(1);
        List<KmListenerEntity> list = listenerService.getAll(MsgType.NM_SVR_BURN_STATE.getType());
        if(!CollectionUtil.isEmpty(list)){
            for (KmListenerEntity kmListenerEntity : list) {
                notifyUtils.offLineNty(kmListenerEntity.getUrl(),burnInfo);
            }
        }
    }
}
