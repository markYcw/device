package com.kedacom.device.core.event;

import com.alibaba.fastjson.JSONException;
import com.kedacom.core.anno.KmNotify;
import com.kedacom.core.pojo.Notify;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/17
 */
@Setter
@Getter
@ToString(callSuper = true)
@KmNotify(name = "devandgroupstatusnty")
public class DeviceAndGroupEvent extends ApplicationEvent implements Notify {

    @ApiModelProperty(value = "设备id")
    private String id;

    @ApiModelProperty(value = "分组id")
    private String groupId;

    @ApiModelProperty(value = "国标id")
    private String gbid;

    public DeviceAndGroupEvent(Object source) {
        super(source);
    }

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
