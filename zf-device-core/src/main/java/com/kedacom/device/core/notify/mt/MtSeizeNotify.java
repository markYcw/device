package com.kedacom.device.core.notify.mt;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.device.core.entity.MtEntity;
import com.kedacom.device.core.mapper.MtMapper;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.impl.MtServiceImpl;
import com.kedacom.mt.SeizeNotifyVo;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author wangxy
 * @describe 终端抢占通知
 * @date 2021/12/3
 */
@Slf4j
public class MtSeizeNotify extends INotify {

    @Resource
    MtMapper mtMapper;

    @Override
    protected void consumeMessage(Integer ssid, String message) {

        SeizeNotifyVo seizeNotifyVo = JSON.parseObject(message, SeizeNotifyVo.class);

        LambdaQueryWrapper<MtEntity> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(MtEntity::getIp, seizeNotifyVo.getContent().getIp());

        MtEntity mtEntity = mtMapper.selectOne(queryWrapper);

        log.info("终端抢占通知, 终端名称 : {}, 终端被抢占端的IP : {}", mtEntity.getName(), seizeNotifyVo.getContent().getIp());

        mtEntity.setMtid(null);

        mtMapper.updateById(mtEntity);

        MtServiceImpl.synHashSet.remove(mtEntity.getId());

        // TODO 发送给前端

    }
}
