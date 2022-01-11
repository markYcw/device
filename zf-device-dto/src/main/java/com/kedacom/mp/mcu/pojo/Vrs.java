package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ycw
 * @date: 2021/10/19 13:28
 * @description 录像信息
 */
@Data
@ApiModel(description =  "vrs信息")
public class Vrs implements Serializable {

    @ApiModelProperty(value = "vrs的id信息")
    private String vrsId;

    @ApiModelProperty(value = "vrs地址")
    private String ip;

    @ApiModelProperty(value = "运营商")
    private Integer operator;

    @ApiModelProperty(value = "vrs端口")
    private Integer port;


}
