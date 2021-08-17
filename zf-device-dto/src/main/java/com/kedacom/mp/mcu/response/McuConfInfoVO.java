package com.kedacom.mp.mcu.response;

import com.kedacom.mp.mcu.pojo.ConfInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/14 15:12
 * @description 获取会议信息响应
 */
@Data
@ApiModel(value = "获取会议信息响应")
public class McuConfInfoVO implements Serializable {

    @ApiModelProperty(value = "会议信息")
    private ConfInfo confInfo;

}
