package com.kedacom.device.core.notify.mt;

import com.alibaba.fastjson.JSON;
import com.kedacom.api.WebsocketFeign;
import com.kedacom.pojo.SystemWebSocketMessage;

import javax.annotation.Resource;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/9
 */
public class MtSendMessage {

    @Resource
    WebsocketFeign websocketFeign;

    public void sendMessage(String message) {

        SystemWebSocketMessage webSocketMessage = new SystemWebSocketMessage();

        webSocketMessage.setMessage(message);

        webSocketMessage.setServerName("device");

        webSocketMessage.setOperationType(7);
        // 向前端发送终端掉线信息
        websocketFeign.sendInfo(JSON.toJSONString(webSocketMessage));

    }

}
