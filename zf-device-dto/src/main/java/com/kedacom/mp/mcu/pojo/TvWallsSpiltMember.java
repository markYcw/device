package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 13:39
 * @description 电视墙列表分屏成员
 */
@Data
@ApiModel(description =  "电视墙列表分屏成员")
public class TvWallsSpiltMember implements Serializable {

    @NotNull(message = "四分屏通道号不能为空")
    @ApiModelProperty(value = "四分屏通道号, 从0开始到3")
    private Integer chnIdx;

    @NotBlank(message = "分屏内终端不能为空")
    @ApiModelProperty(value = "分屏内终端 最大字符长度：48个字节")
    private String mtId;

}
