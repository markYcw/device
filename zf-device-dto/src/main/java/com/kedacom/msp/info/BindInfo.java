package com.kedacom.msp.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 14:56
 */
@Data
@ApiModel
public class BindInfo implements Serializable {

    @ApiModelProperty(value = "解码器ID")
    private String decid;

}
