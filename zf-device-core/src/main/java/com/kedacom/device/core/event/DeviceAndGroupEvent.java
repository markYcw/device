package com.kedacom.device.core.event;

import com.alibaba.fastjson.JSONException;
import com.kedacom.core.anno.KmNotify;
import com.kedacom.core.pojo.Notify;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.apache.catalina.LifecycleState;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/17
 */
@Data
@ToString(callSuper = true)
@KmNotify(name = "devandgroupstatusnty")
public class DeviceAndGroupEvent implements Notify {

    private List<DevAndGroup> devandgroup;

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
