package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/11
 */
@Data
@ApiModel(value = "查询告警类型信息响应参数类")
public class UmsAlarmTypeQueryResponseDto implements Serializable {

    @ApiModelProperty(value = "报警类型标识")
    private String alarmCode;

    @ApiModelProperty(value = "报警类型描述")
    private String alarmDesc;

}
