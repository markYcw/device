package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
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
public class ClearDiscussionGroupRequest extends BaseRequest {

    private static final String COMMAND = "cleardiscussion";

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @Override
    public String name() {
        return COMMAND;
    }
}
