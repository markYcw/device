package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hxj
 * @date: 2021/8/17 10:46
 * @description 添加/删除终端入参
 */
@Data
@ApiModel(description =  "添加/删除终端入参")
public class McuMtDTO extends McuRequestDTO {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：添加终端;1：删除终端",required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码",required = true)
    private String confId;

    @ApiModelProperty(value = "终端id 终端ID，删除时必填")
    private String mtId;

    @ApiModelProperty(value = "终端号")
    private String mtAccount;

    @ApiModelProperty(value = "终端类型 5-e164号码；6-电话；7-ip地址；8-别名@ip(监控前端)；")
    private Integer mtType;

    @ApiModelProperty(value = "呼叫码率，添加时必填")
    private Integer bitrate;

    @ApiModelProperty(value = "呼叫协议 0-H323；1-SIP；")
    private Integer protocol;

}
