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
 * @Auther: hxj
 * @Date: 2021/5/20 15:34
 */
@Data
@ToString(callSuper = true)
@KmNotify(name = "transdatanty")
public class TransDataNotifyEvent implements Notify {

    private NotifyHead nty;

    @ApiModelProperty(value = "设备ID")
    private String DeviceID;

    @ApiModelProperty(value = "控制类型。当Ext为1时AppCmd不起作用")
    private Integer AppCmd;

    @ApiModelProperty(value = "透明数据")
    private String TransData;

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
