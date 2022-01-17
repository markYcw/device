package com.kedacom.device.core.notify.svr;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.mapper.SvrMapper;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.utils.ContextUtils;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.deviceListener.msgType.MsgType;
import com.kedacom.pojo.SystemWebSocketMessage;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.stragegy.svr.pojo.AudioAct;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:40
 * @description 5.35语音激励通知
 */
@Slf4j
public class AudioActNotify extends INotify {
    @Override
    public void consumeMessage(Integer ssid,String message) {
        AudioAct audioAct = JSON.parseObject(message, AudioAct.class);
        SvrMapper svrMapper = ContextUtils.getBean(SvrMapper.class);
        RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
        DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
        LambdaQueryWrapper<SvrEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SvrEntity::getSsid,ssid);
        SvrEntity entity = svrMapper.selectList(wrapper).get(DevTypeConstant.getZero);
        //发送webSocket给前端
        SystemWebSocketMessage msg = new SystemWebSocketMessage();
        msg.setOperationType(9);
        msg.setServerName("device");
        msg.setData(audioAct);
        log.info("===============发送语音激励通知webSocket给前端{}", JSON.toJSONString(message));
        //将通知发给业务
        audioAct.setMsgType(MsgType.SVR_BURN_STATE_NTY.getType());
        audioAct.setDbId(entity.getId());
        List<KmListenerEntity> list = listenerService.getAll(MsgType.SVR_DEVICE_STATE_NTY.getType());
        if(!CollectionUtil.isEmpty(list)){
            for (KmListenerEntity kmListenerEntity : list) {
                notifyUtils.offLineNty(kmListenerEntity.getUrl(),audioAct);
            }
        }
    }
}
