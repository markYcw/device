package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 10:46
 * @description 添加/删除终端向中间件请求参数
 */
@Data
@ApiModel(value = "添加/删除终端向中间件请求参数")
public class McuMtRequest implements Serializable {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：添加终端;1：删除终端",required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码",required = true)
    private String confId;

    @NotBlank(message = "终端id不能为空")
    @ApiModelProperty(value = "终端id")
    private String mtId;

    @ApiModelProperty(value = "终端号")
    private String mtAccount;

    @ApiModelProperty(value = "终端类型\n" +
            "5-e164号码；\n" +
            "6-电话；\n" +
            "7-ip地址；\n" +
            "8-别名@ip(监控前端)；")
    private Integer mtType;

    @ApiModelProperty(value = "呼叫码率，添加时必填")
    private Integer bitrate;

    @ApiModelProperty(value = "呼叫协议\n" +
            "0-H323；\n" +
            "1-SIP；")
    private Integer protocol;

}
