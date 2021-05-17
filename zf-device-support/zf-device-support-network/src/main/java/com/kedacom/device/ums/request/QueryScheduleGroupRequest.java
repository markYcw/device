package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class QueryScheduleGroupRequest extends BaseRequest {

    private static final String COMMAND = "querygroup";

    @Override
    public String name() {
        return COMMAND;
    }

}
