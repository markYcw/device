package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/16 10:51
 * @description 参会成员
 */
@Data
@ApiModel(description =  "参会成员")
public class InviteMember implements Serializable {

    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称 最大字符长度：128个字节",required = true)
    private String name;

    @NotBlank(message = "帐号不能为空")
    @ApiModelProperty(value = "帐号 最大字符长度：128个字节",required = true)
    private String account;

    @NotNull(message = "帐号类型不能为空")
    @ApiModelProperty(value = "帐号类型:1-moid；4-非系统邮箱；5-e164号码；6-电话；7-ip地址；8-别名@ip(监控前端)；",required = true)
    private Integer accountType;

    @NotNull(message = "终端呼叫码率不能为空")
    @ApiModelProperty(value = "终端呼叫码率，不可超过会议码率，默认为会议码率",required = true)
    private Integer bitrate;

    @NotNull(message = "呼叫协议不能为空")
    @ApiModelProperty(value = "呼叫协议:0-H323；1-SIP；",required = true)
    private Integer protocol;

}
