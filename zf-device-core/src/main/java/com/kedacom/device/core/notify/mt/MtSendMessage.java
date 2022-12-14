package com.kedacom.device.core.notify.mt;

import com.alibaba.fastjson.JSON;
import com.kedacom.api.WebsocketFeign;
import com.kedacom.pojo.SystemWebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/9
 */
@Slf4j
@Component
public class MtSendMessage {

    @Resource
    WebsocketFeign websocketFeign;

    public void sendMessage(String message) {

        SystemWebSocketMessage webSocketMessage = new SystemWebSocketMessage();

        webSocketMessage.setMessage(message);

        webSocketMessage.setServerName("device");

        webSocketMessage.setOperationType(8);
        // 向前端发送终端掉线信息
        log.info("向前端发送终端通知信息");

        websocketFeign.sendInfo(JSON.toJSONString(webSocketMessage));

    }

}
