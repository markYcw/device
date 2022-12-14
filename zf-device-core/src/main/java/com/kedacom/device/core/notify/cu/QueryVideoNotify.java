package com.kedacom.device.core.notify.cu;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.api.WebsocketFeign;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.vo.QueryCuVideoVo;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.mapper.CuMapper;
import com.kedacom.device.core.notify.cu.loadGroup.notify.QueryCuVideoNotify;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.QueryVideoInsideNotify;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.CuService;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.utils.ContextUtils;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.deviceListener.msgType.MsgType;
import com.kedacom.pojo.SystemWebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/12/2 19:28
 * @description 查询录像通知
 */
@Slf4j
public class QueryVideoNotify extends INotify{
    @Override
    protected void consumeMessage(Integer ssid, String message) {
        CuService service = ContextUtils.getBean(CuService.class);
        CuEntity entity = service.getBySsid(ssid);
        if(ObjectUtil.isNull(entity)){
            return;
        }
        QueryCuVideoNotify queryCuVideoNotify = JSON.parseObject(message, QueryCuVideoNotify.class);
        QueryVideoInsideNotify content = queryCuVideoNotify.getContent();
        QueryCuVideoVo vo = new QueryCuVideoVo();
        BeanUtils.copyProperties(content,vo);
        RegisterListenerService listenerService = ContextUtils.getBean(RegisterListenerService.class);
        DeviceNotifyUtils notifyUtils = ContextUtils.getBean(DeviceNotifyUtils.class);
        //将通知发给业务
        vo.setDbId(entity.getId());
        List<KmListenerEntity> list = listenerService.getAll(MsgType.CU_QUERY_VIDEO_NTY.getType());
        if(!CollectionUtil.isEmpty(list)){
            for (KmListenerEntity kmListenerEntity : list) {
                notifyUtils.cuDeviceNty(kmListenerEntity.getUrl(),vo);
            }
        }
        //发送webSocket给前端
        WebsocketFeign websocketFeign = ContextUtils.getBean(WebsocketFeign.class);
        SystemWebSocketMessage sws = new SystemWebSocketMessage();
        sws.setOperationType(7);
        sws.setServerName("device");
        sws.setData(vo);
        log.info("===============发送CU录像通知webSocket给前端{}", sws);
        websocketFeign.sendInfo(JSON.toJSONString(sws));
    }
}
