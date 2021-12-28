package com.kedacom.device.core.notify.mt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.device.core.entity.MtEntity;
import com.kedacom.device.core.mapper.MtMapper;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.impl.MtServiceImpl;
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

    @Resource
    MtSendMessage mtSendMessage;

    @Override
    protected void consumeMessage(Integer ssid, String message) {

        LambdaQueryWrapper<MtEntity> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(MtEntity::getMtid, ssid);

        MtEntity mtEntity = mtMapper.selectOne(queryWrapper);

        log.info("终端掉线通知, 终端名称 : {}", mtEntity.getName());

        mtEntity.setMtid(null);
        // 将该终端与中间件交互的 mtId 修改为 null
        mtMapper.updateById(mtEntity);
        // 将该终端从维护心跳的缓存中删除
        MtServiceImpl.synHashSet.remove(mtEntity.getId());

        String msg = mtEntity.getName() + " 终端已掉线！";
        // 向前端发送终端掉线通知
        mtSendMessage.sendMessage(msg);

    }

}
