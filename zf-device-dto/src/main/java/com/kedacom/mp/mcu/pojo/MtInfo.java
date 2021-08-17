package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 11:11
 * @description 终端
 */
@Data
@ApiModel(value = "终端")
public class MtInfo implements Serializable {

    @ApiModelProperty(value = "终端ip或者e164号")
    private String mtId;

}
