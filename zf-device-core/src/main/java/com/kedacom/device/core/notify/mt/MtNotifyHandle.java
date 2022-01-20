package com.kedacom.device.core.notify.mt;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.device.core.entity.MtEntity;
import com.kedacom.device.core.mapper.MtMapper;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.MtService;
import com.kedacom.device.core.service.impl.MtServiceImpl;
import com.kedacom.device.core.utils.ContextUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangxy
 * @describe 终端通知
 * @date 2022/1/18
 */
@Slf4j
public class MtNotifyHandle extends INotify {

    /**
     * 终端被抢占
     */
    private static final Integer SEIZE = 100;

    /**
     * 终端掉线
     */
    private static final Integer DROP_LINE = 1;


    @Override
    protected void consumeMessage(Integer ssid, String message) {

        log.info("终端通知信息 : {}", message);
        MtMapper mtMapper = ContextUtils.getBean(MtMapper.class);
        MtService mtService = ContextUtils.getBean(MtService.class);
        MtSendMessage mtSendMessage = ContextUtils.getBean(MtSendMessage.class);

        JSONObject jsonObject = JSONObject.parseObject(message);
        Integer msgType = (Integer) jsonObject.get("msgType");

        MtServiceImpl.MT_CHECK_SWITCH.set(false);
        String type = DROP_LINE.equals(msgType) ? "掉线" : "被抢占";
        LambdaQueryWrapper<MtEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MtEntity::getMtid, ssid);

        MtEntity mtEntity = mtMapper.selectOne(queryWrapper);
        if (mtEntity == null) {
            log.error("该终端离线或不存在");
            return;
        }

        log.info("终端" + type + "通知, 终端名称 : {}", mtEntity.getName());
        if (MtServiceImpl.MT_MAINTAIN_HEARTBEAT_PERIOD.get()) {
            // 在心跳维护期间，如有通知则将终端id添加到无效缓存中，心跳维护结束后将统一从在线终端缓存中删除
            MtServiceImpl.synInvalidHashSet.add(mtEntity.getId());
        } else {
            // 不在心跳维护期间，如有通知则将终端id直接从在线终端缓存中删除
            MtServiceImpl.synHashSet.remove(mtEntity.getId());
        }
        try {
            // 登出终端
            mtService.logOutById(mtEntity.getId());
        } catch (Exception e) {
            log.error("消费通知后退出终端失败 : {}", e.getMessage());
        }
        MtServiceImpl.MT_CHECK_SWITCH .set(true);
        String msg = mtEntity.getName() + " 终端已" + type;

        // 向前端发送终端被抢占登录通知
        mtSendMessage.sendMessage(msg);
    }

}
