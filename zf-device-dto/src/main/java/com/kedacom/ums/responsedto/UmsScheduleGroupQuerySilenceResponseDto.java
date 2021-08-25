package com.kedacom.ums.responsedto;

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
@ApiModel(description =  "查询调度组静音响应参数类")
public class UmsScheduleGroupQuerySilenceResponseDto implements Serializable {

    @ApiModelProperty(value = "状态（1:静音 0:不静音）")
    private Integer SilenceState;

}
