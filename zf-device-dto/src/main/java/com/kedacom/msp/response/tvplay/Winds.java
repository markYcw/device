package com.kedacom.msp.response.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description =  "窗口信息")
public class Winds implements Serializable {

    @ApiModelProperty(value = "窗口ID")
    private Integer wndid;

}