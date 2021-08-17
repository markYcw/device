package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 11:05
 * @description 哑音向中间件请求参数
 */
@Data
@ApiModel(value = "哑音向中间件请求参数")
public class McuMuteRequest implements Serializable {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：单个，1：全场", required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @NotBlank(message = "终端ip或者e164号不能为空")
    @ApiModelProperty(value = "终端ip或者e164号", required = true)
    private String mtId;

    @NotNull(message = "哑音状态不能为空")
    @ApiModelProperty(value = "0：哑音，1：非哑音", required = true)
    private Integer state;

}
