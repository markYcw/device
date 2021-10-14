package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 13:28
 * @description 电视墙
 */
@Data
@ApiModel(description =  "电视墙")
public class TvWall implements Serializable {

    @ApiModelProperty(value = "电视墙id")
    private String hduId;

    @ApiModelProperty(value = "电视墙别名")
    private String hduName;

    @ApiModelProperty(value = "是否被占用:0-否；1-是；")
    private Integer occupy;

    @ApiModelProperty(value = "是否在线:0-否；1-是；")
    private Integer online;

    @ApiModelProperty(value = "电视墙信息，电视墙在会议中时有效")
    private Chns hdu;

}
