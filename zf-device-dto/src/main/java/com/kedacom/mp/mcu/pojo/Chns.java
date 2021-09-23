package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 13:29
 * @description 通道信息
 */
@Data
@ApiModel(description =  "通道信息")
public class Chns implements Serializable {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "电视墙模式：1-选看；2-四分屏(仅传统会议有效)；3-单通道轮询；",required = true)
    private Integer mode;

    @ApiModelProperty(value = "选看参数")
    private Specific specific;

    @ApiModelProperty(value = "轮询信息")
    private Poll poll;

    @ApiModelProperty(value = "四分屏")
    private Spilt spilt;

}
