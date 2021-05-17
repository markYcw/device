package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/14
 */
@Data
@ToString(callSuper = true)
public class QueryVmpMixRequest extends BaseRequest {

    private static final String COMMAND = "queryvmpmix";

    private String GroupID;

    @Override
    public String name() {
        return COMMAND;
    }

}
