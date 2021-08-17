package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/16 10:38
 * @description 混音信息
 */
@Data
@ApiModel(value = "混音信息")
public class Mix implements Serializable {

    @ApiModelProperty(value = "混音模式:-智能混音；2-定制混音；")
    private Integer mode;

    @ApiModelProperty(value = "制定混音时的混音成员列表")
    private List<MixMember> members;

}
