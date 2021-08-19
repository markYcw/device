package com.kedacom.mp.mcu.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/19 13:15
 * @description 添加/删除终端响应
 */
@Data
@ApiModel(value = "添加/删除终端响应")
public class McuMtVO implements Serializable {

    @ApiModelProperty(value = "终端ID")
    private String mtId;

}
