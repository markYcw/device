package com.kedacom.device.core.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.kedacom.core.anno.KmNotify;
import com.kedacom.core.pojo.Notify;
import com.kedacom.core.pojo.NotifyHead;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 15:53
 * @description
 */
@Data
@ToString(callSuper = true)
@KmNotify(name = "audioactnty")
public class AudioActEvent implements Notify {

    private NotifyHead nty;

    @ApiModelProperty(value = "激励开关 0：关 1：开")
    private Integer ActCfg;

    @ApiModelProperty(value = "激励通道")
    private Integer ActChn;

    @ApiModelProperty(value = "功率")
    private Integer Power;

    @ApiModelProperty(value = "设备ID")
    private String DeviceID;

    private JSONObject removeHead() {

        String json = JSON.toJSONString(this);
        JSONObject jsonObject = JSON.parseObject(json);

        if (!jsonObject.containsKey("nty")) {
            throw new IllegalArgumentException("this object is not resp type");
        }
        jsonObject.remove("nty");
        return jsonObject;

    }

    @Override
    public Integer acquireSsno() {
        return nty.getSsno();
    }

    @Override
    public String acquireCommand() {
        return nty.getName();
    }

    @Override
    public <T> T acquireData(Class<T> clazz) throws JSONException {
        JSONObject jsonObject = removeHead();
        return jsonObject.toJavaObject(clazz);
    }
}
