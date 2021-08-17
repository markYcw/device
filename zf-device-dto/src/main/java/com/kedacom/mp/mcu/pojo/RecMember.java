package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 13:21
 * @description 录像终端数组
 */
@Data
@ApiModel(value = "录像终端")
public class RecMember implements Serializable {

    @ApiModelProperty(value = "终端ip或者e164")
    private String mtId;

}
