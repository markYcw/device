package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.BaseResult;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.convert.RegisterListenerConvert;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.mapper.KmListenerMapper;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.deviceListener.RegisterListenerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("registerListenerService")
public class RegisterListenerServiceImpl extends ServiceImpl<KmListenerMapper, KmListenerEntity> implements RegisterListenerService {

    @Autowired
    private KmListenerMapper mapper;

    @Autowired
    private RegisterListenerConvert convert;

    @Override
    public List<KmListenerEntity> getAll() {
        return mapper.getAll();
    }

    @Override
    public BaseResult<RegisterListenerVo> registerListener(RegisterListenerVo vo) {
        log.info("============设备消息订阅入参RegisterListenerVo：{}",vo);
        if(!isRepeat(vo)){
            RegisterListenerVo listenerVo = new RegisterListenerVo();
            listenerVo.setError(10010);
            return BaseResult.failed(1,"重复注册",listenerVo);
        }
        KmListenerEntity entity = convert.convertToEntity(vo);
        mapper.insert(entity);
        Integer id = entity.getId();
        vo.setId(id);
        vo.setError(DevTypeConstant.updateRecordKey);
        return BaseResult.succeed("订阅设备消息成功",vo);
    }

    @Override
    public boolean isRepeat(RegisterListenerVo registerUrlVo) {
        Integer id = registerUrlVo.getId();
        String url = registerUrlVo.getUrl();
        LambdaQueryWrapper<KmListenerEntity> wrapper = new LambdaQueryWrapper<>();
        if (id == null) {
            wrapper.eq(KmListenerEntity::getUrl,url);
            List<KmListenerEntity> insertList = mapper.selectList(wrapper);
            if(CollectionUtil.isNotEmpty(insertList)){
                log.info("===============注册KM设备订阅监听时IP重复=================");
                return false;
            }
        }
        return true;
    }

    @Override
    public void UnRegisterListener(RegisterListenerVo registerUrlVo) {
        log.info("============取消设备消息订阅入参ip：{}",registerUrlVo);
        mapper.deleteById(registerUrlVo.getId());
    }
}
