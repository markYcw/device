package com.kedacom.device.core.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.kedacom.core.anno.KmNotify;
import com.kedacom.core.pojo.Notify;
import com.kedacom.core.pojo.NotifyHead;
import com.kedacom.device.ums.DeviceGroupVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/17
 */
@Data
@ToString(callSuper = true)
@KmNotify(name = "devgroupnty")
public class DeviceGroupEvent implements Notify {

    private NotifyHead nty;

    @ApiModelProperty(value = "总数")
    private Integer count;

    @ApiModelProperty(value = "是否结束")
    private Integer end;

    private List<DeviceGroupVo> result;

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
