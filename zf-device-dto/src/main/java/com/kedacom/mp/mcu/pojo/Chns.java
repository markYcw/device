package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 13:29
 * @description 通道信息
 */
@Data
@ApiModel(value = "通道信息")
public class Chns implements Serializable {

    @ApiModelProperty(value = "电视墙模式：1-选看；2-四分屏(仅传统会议有效)；3-单通道轮询；")
    private Integer mode;

    @ApiModelProperty(value = "选看参数")
    private Specific specific;

    @ApiModelProperty(value = "轮询信息")
    private Poll poll;

    @ApiModelProperty(value = "四分屏")
    private Spilt spilt;

}
