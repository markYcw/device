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
import com.kedacom.svr.stragegy.svr.pojo.SvrDeviceStatus;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:32
 * @description 编解码设备上报通知
 */
public class DeviceStatusNotify extends INotify {
    @Override
    public void consumeMessage(Integer ssid, String message) {
        SvrDeviceStatus status = JSON.parseObject(message, SvrDeviceStatus.class);
        SvrService service = ContextUtils.getBean(SvrService.class);
        RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
        DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
        SvrEntity entity = service.getBySsid(ssid);
        //将通知发给业务
        status.setMsgType(MsgType.SVR_DEVICE_STATE_NTY.getType());
        status.setDbId(entity.getId());
        List<KmListenerEntity> list = listenerService.getAll(MsgType.SVR_DEVICE_STATE_NTY.getType());
        if(!CollectionUtil.isEmpty(list)){
            for (KmListenerEntity kmListenerEntity : list) {
                notifyUtils.offLineNty(kmListenerEntity.getUrl(),status);
            }
        }
    }
}
