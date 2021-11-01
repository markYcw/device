package com.kedacom.device.core.notify.cu;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.mapper.CuMapper;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.utils.ContextUtils;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.deviceListener.msgType.MsgType;
import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/29 16:56
 * @description cu 掉线通知
 */
public class OffLineNotify extends INotify {
    /**
     * cu掉线以后状态置为离线并发送通知给业务
     * @param ssid
     * @param message
     */
    @Override
    public void consumeMessage(Integer ssid, String message) {
        CuMapper cuMapper = ContextUtils.getBean(CuMapper.class);
        RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
        DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
        LambdaQueryWrapper<CuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CuEntity::getSsid,ssid);
        CuEntity cuEntity = cuMapper.selectList(wrapper).get(DevTypeConstant.getZero);
        //收到掉线通知后将ssid清除
        cuEntity.setSsid(null);
        cuMapper.updateById(cuEntity);
        //将通知发给业务
        DeviceNotifyRequestDTO notifyRequestDTO = new DeviceNotifyRequestDTO();
        notifyRequestDTO.setDbId(cuEntity.getId().longValue());
        List<KmListenerEntity> list = listenerService.getAll(MsgType.CU_OFF_LINE.getType());
        if(!CollectionUtil.isEmpty(list)){
            for (KmListenerEntity kmListenerEntity : list) {
                notifyUtils.offLineNty(kmListenerEntity.getUrl(),notifyRequestDTO);
            }
        }

    }
}
