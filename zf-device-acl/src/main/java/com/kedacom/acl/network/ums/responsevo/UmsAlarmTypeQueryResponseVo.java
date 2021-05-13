package com.kedacom.acl.network.ums.responsevo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
@Data
@ApiModel
public class UmsAlarmTypeQueryResponseVo implements Serializable {

    @ApiModelProperty(value = "报警类型标识")
    private String alarmCode;

    @ApiModelProperty(value = "报警类型描述")
    private String alarmDesc;

}
