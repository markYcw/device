package com.kedacom.device.core.notify.cu;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.kedacom.BaseResult;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.cu.dto.CuRequestDto;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.vo.DevEntityVo;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.mapper.CuMapper;
import com.kedacom.device.core.notify.cu.loadGroup.CuDeviceLoadThread;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.CuService;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.service.impl.CuServiceImpl;
import com.kedacom.device.core.utils.ContextUtils;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.deviceListener.msgType.MsgType;
import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/29 16:56
 * @description cu 掉线通知
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
        CuMapper cuMapper = ContextUtils.getBean(CuMapper.class);
        CuDeviceLoadThread cuDeviceLoadThread = ContextUtils.getBean(CuDeviceLoadThread.class);
        RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
        DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
        LambdaQueryWrapper<CuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CuEntity::getSsid,ssid);
        CuEntity cuEntity = cuMapper.selectList(wrapper).get(DevTypeConstant.getZero);
        //除去cu设备状态池中的状态
        CuServiceImpl.cuDeviceStatusPoll.remove(cuEntity.getId());
        //去除会话信息
        cuDeviceLoadThread.getCuClient().getSessionManager().removeSession(ssid);
        //去除心跳定时任务
        ScheduledThreadPoolExecutor poolExecutor = CuServiceImpl.cuHbStatusPoll.get(cuEntity.getId());
        if(ObjectUtils.isNotNull(poolExecutor)){
            poolExecutor.shutdownNow();
            CuServiceImpl.cuHbStatusPoll.remove(cuEntity.getId());
        }
        //将通知发给业务
        DeviceNotifyRequestDTO notifyRequestDTO = new DeviceNotifyRequestDTO();
        notifyRequestDTO.setDbId(cuEntity.getId());
        List<KmListenerEntity> list = listenerService.getAll(MsgType.CU_OFF_LINE.getType());
        if(!CollectionUtil.isEmpty(list)){
            for (KmListenerEntity kmListenerEntity : list) {
                try {
                    CompletableFuture.runAsync(()->notifyUtils.offLineNty(kmListenerEntity.getUrl(),notifyRequestDTO));
                } catch (Exception e) {
                    log.error("==========发送CU掉线通知给业务时失败{}",e);
                }
            }
        }
        //下面将进行自动重连
       this.reTryLogin(cuEntity.getId());

    }

    /**
     * 根据数据库ID自动重连每一分钟重连一次
     * @param dbId
     */
    public void reTryLogin(Integer dbId){
        log.info("=====================CU即将进行自动重连数据库ID为：{}",dbId);
        CuService cuService = ContextUtils.getBean(CuService.class);
        cuService.reTryLoginNow(dbId);
    }
}
