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
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.stragegy.svr.pojo.DvdStatus;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:39
 * @description 5.28DVD状态通知
 */
public class DvdStatusNotify extends INotify {
    @Override
    public void consumeMessage(Integer ssid,String message) {
        DvdStatus dvdStatus = JSON.parseObject(message, DvdStatus.class);
        SvrMapper svrMapper = ContextUtils.getBean(SvrMapper.class);
        RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
        DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
        LambdaQueryWrapper<SvrEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SvrEntity::getSsid,ssid);
        SvrEntity entity = svrMapper.selectList(wrapper).get(DevTypeConstant.getZero);
        //将通知发给业务
        dvdStatus.setMsgType(MsgType.SVR_DVD_STATE_NTY.getType());
        dvdStatus.setDbId(entity.getId());
        List<KmListenerEntity> list = listenerService.getAll(MsgType.SVR_DEVICE_STATE_NTY.getType());
        if(!CollectionUtil.isEmpty(list)){
            for (KmListenerEntity kmListenerEntity : list) {
                notifyUtils.offLineNty(kmListenerEntity.getUrl(),dvdStatus);
            }
        }
    }
}
