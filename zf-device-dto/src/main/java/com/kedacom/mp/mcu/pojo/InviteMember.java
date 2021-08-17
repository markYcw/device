package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/16 10:51
 * @description 参会成员
 */
@Data
@ApiModel(value = "参会成员")
public class InviteMember implements Serializable {

    @ApiModelProperty(value = "名称 最大字符长度：128个字节")
    private String name;

    @ApiModelProperty(value = "帐号 最大字符长度：128个字节")
    private String account;

    @ApiModelProperty(value = "帐号类型:1-moid；4-非系统邮箱；5-e164号码；6-电话；7-ip地址；8-别名@ip(监控前端)；")
    private Integer accountType;

}
