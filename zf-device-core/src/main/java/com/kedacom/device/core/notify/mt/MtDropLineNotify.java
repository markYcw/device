package com.kedacom.device.core.notify.mt;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.device.core.entity.MtEntity;
import com.kedacom.device.core.mapper.MtMapper;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.impl.MtServiceImpl;
import com.kedacom.mt.DropLineNotifyVo;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author wangxy
 * @describe 终端掉线通知
 * @date 2021/12/3
 */
@Slf4j
public class MtDropLineNotify extends INotify {

    @Resource
    MtMapper mtMapper;

    @Override
    protected void consumeMessage(Integer ssid, String message) {

        DropLineNotifyVo dropLineNotifyVo = JSON.parseObject(message, DropLineNotifyVo.class);

        LambdaQueryWrapper<MtEntity> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(MtEntity::getIp, dropLineNotifyVo.getContent().getIp());

        MtEntity mtEntity = mtMapper.selectOne(queryWrapper);

        log.info("终端掉线通知, 终端名称 : {}, 终端IP : {}", mtEntity.getName(), mtEntity.getIp());

        mtEntity.setMtid(null);

        mtMapper.updateById(mtEntity);

        MtServiceImpl.synHashSet.remove(mtEntity.getId());

        // TODO 发送给前端

    }

}
