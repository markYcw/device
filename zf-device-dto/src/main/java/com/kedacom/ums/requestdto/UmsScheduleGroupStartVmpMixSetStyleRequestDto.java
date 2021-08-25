package com.kedacom.ums.requestdto;

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
@ApiModel(description =  "开始画面合成-风格设置参数类")
public class UmsScheduleGroupStartVmpMixSetStyleRequestDto implements Serializable {

    @ApiModelProperty(value = "字体大小（0-小；1-中；2-大）默认为1")
    private Integer fontSize;

    @ApiModelProperty(value = "字体颜色，三原色#RGB格式，默认为#FFFFFF白色")
    private String fontColor;

    @ApiModelProperty(value = "台标显示位置（0-左上角，1-左小角，2-右上角，3-右 下角，4-底部中间）")
    private Integer position;

}
