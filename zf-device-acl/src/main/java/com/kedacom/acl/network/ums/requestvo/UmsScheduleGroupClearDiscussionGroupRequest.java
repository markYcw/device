package com.kedacom.acl.network.ums.requestvo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "清空讨论组请求参数类")
public class UmsScheduleGroupClearDiscussionGroupRequest implements Serializable {

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

}
