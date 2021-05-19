package com.kedacom.device.core.event;

import com.alibaba.fastjson.JSONException;
import com.kedacom.core.anno.KmNotify;
import com.kedacom.core.pojo.Notify;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/17
 */
@Data
@ToString(callSuper = true)
@KmNotify(name = "devandgroupstatusnty")
public class DeviceAndGroupEvent implements Notify {

    @ApiModelProperty(value = "设备id")
    private String id;

    @ApiModelProperty(value = "分组id")
    private String groupId;

    @ApiModelProperty(value = "国标id")
    private String gbid;

    @Override
    public Integer acquireSsno() {
        return null;
    }

    @Override
    public String acquireCommand() {
        return null;
    }

    @Override
    public <T> T acquireData(Class<T> clazz) throws JSONException {
        return null;
    }
}
