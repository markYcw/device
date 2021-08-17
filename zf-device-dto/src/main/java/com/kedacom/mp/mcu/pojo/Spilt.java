package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 13:34
 * @description 四分屏
 */
@Data
@ApiModel(value = "四分屏")
public class Spilt implements Serializable {

    @ApiModelProperty(value = "分屏成员")
    private List<TvWallsSpiltMember> members;

}
