package com.kedacom.device.core.notify.svr;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.service.SvrService;
import com.kedacom.device.core.utils.ContextUtils;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.deviceListener.msgType.MsgType;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.stragegy.svr.pojo.BurnStatus;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:37
 * @description 5.22刻录任务通知
 */
public class BurnTaskStatusNotify extends INotify {
    @Override
    public void consumeMessage(Integer ssid,String message) {
        BurnStatus burnStatus = JSON.parseObject(message, BurnStatus.class);
        SvrService service = ContextUtils.getBean(SvrService.class);
        RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
        DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
        SvrEntity entity = service.getBySsid(ssid);
        //将通知发给业务
        burnStatus.setMsgType(MsgType.SVR_BURN_STATE_NTY.getType());
        burnStatus.setDbId(entity.getId());
        List<KmListenerEntity> list = listenerService.getAll(MsgType.SVR_BURN_STATE_NTY.getType());
        if(!CollectionUtil.isEmpty(list)){
            for (KmListenerEntity kmListenerEntity : list) {
                notifyUtils.offLineNty(kmListenerEntity.getUrl(),burnStatus);
            }
        }
    }
}
