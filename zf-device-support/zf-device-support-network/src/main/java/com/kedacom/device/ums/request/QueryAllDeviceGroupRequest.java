package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import lombok.Data;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/14
 */
@ToString(callSuper = true)
@Data
public class QueryAllDeviceGroupRequest extends BaseRequest {

    private static final String COMMAND = "getalldevgroup";

    @Override
    public String name() {
        return COMMAND;
    }

}
