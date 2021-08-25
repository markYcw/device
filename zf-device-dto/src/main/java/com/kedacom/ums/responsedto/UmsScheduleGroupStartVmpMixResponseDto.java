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
@ApiModel(description =  "开始画面合成响应参数类")
public class UmsScheduleGroupStartVmpMixResponseDto implements Serializable {

    @ApiModelProperty(value = "画面合成资源ID")
    private String vmpId;

}
