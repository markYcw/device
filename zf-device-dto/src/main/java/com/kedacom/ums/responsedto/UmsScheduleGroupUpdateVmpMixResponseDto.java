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
@ApiModel(value = "更新画面合成响应参数类")
public class UmsScheduleGroupUpdateVmpMixResponseDto implements Serializable {

    @ApiModelProperty(value = "画面合成资源ID")
    private String mixId;

}
