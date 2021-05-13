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
@ApiModel(value = "查询调度组哑音信息响应参数类")
public class UmsScheduleGroupQueryMuteResponseDto implements Serializable {

    @ApiModelProperty(value = "状态（1:哑音 0:不哑音）")
    private Integer muteState;

}
