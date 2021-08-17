package com.kedacom.device.mp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author hxj
 * @Date: 2021/8/12 9:38
 * @Description 会议统一返回结果
 */
@Data
@ApiModel(value = "会议统一返回结果")
public class MpResponse implements Serializable {

    @ApiModelProperty(value = "流水号")
    private String ssno;

    @ApiModelProperty(value = "错误码,0表示成功")
    private Integer code;

}
