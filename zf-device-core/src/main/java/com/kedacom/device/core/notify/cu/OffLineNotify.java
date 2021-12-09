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
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
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

    //CU重连任务池 若是登录的时候报密码错误则从此状态池中移除
    public static ConcurrentHashMap<Integer,ScheduledThreadPoolExecutor> reTryPoll = new ConcurrentHashMap<>();

    /**
     * cu掉线以后状态置为离线并发送通知给业务
     * @param ssid
     * @param message
     */
    @Override
    public void consumeMessage(Integer ssid, String message) {
        CuMapper cuMapper = ContextUtils.getBean(CuMapper.class);
        CuDeviceLoadThread cuDeviceLoadThread = ContextUtils.getBean(CuDeviceLoadThread.class);
        RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
        DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
        LambdaQueryWrapper<CuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CuEntity::getSsid,ssid);
        CuEntity cuEntity = cuMapper.selectList(wrapper).get(DevTypeConstant.getZero);
        //收到掉线通知后将ssid清除
        cuEntity.setSsid(null);
        cuMapper.updateById(cuEntity);
        //除去cu状态池中已登录状态
        CuServiceImpl.cuStatusPoll.remove(cuEntity.getId());
        //除去cu设备状态池中的状态
        CuServiceImpl.cuDeviceStatusPoll.remove(cuEntity.getId());
        //去除会话信息
        cuDeviceLoadThread.getCuClient().getSessionManager().removeSession(ssid);
        //去除心跳定时任务
        Timer timer = CuServiceImpl.cuHbStatusPoll.get(cuEntity.getId());
        if(ObjectUtils.isNotNull(timer)){
            timer.cancel();
            CuServiceImpl.cuHbStatusPoll.remove(cuEntity.getId());
        }
        //将通知发给业务
        DeviceNotifyRequestDTO notifyRequestDTO = new DeviceNotifyRequestDTO();
        notifyRequestDTO.setDbId(cuEntity.getId());
        List<KmListenerEntity> list = listenerService.getAll(MsgType.CU_OFF_LINE.getType());
        if(!CollectionUtil.isEmpty(list)){
            for (KmListenerEntity kmListenerEntity : list) {
                notifyUtils.offLineNty(kmListenerEntity.getUrl(),notifyRequestDTO);
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
        CuService service = ContextUtils.getBean(CuService.class);
        CuRequestDto dto = new CuRequestDto();
        dto.setKmId(dbId);
        //首先登出
        service.logoutById(dto);
        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(2);
        reTryPoll.put(dbId,scheduled);
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                BaseResult<DevEntityVo> baseResult = service.loginById(dto);
                if(baseResult.getErrCode()==0){
                    scheduled.shutdownNow();
                }
            }
        },60,60, TimeUnit.MILLISECONDS);
    }
}
