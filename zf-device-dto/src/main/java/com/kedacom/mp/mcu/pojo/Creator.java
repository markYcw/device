package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/14 16:04
 * @description 会议发起者
 */
@Data
@ApiModel(value = "会议发起者")
public class Creator implements Serializable {

    @ApiModelProperty(value = "名称 最大字符长度：128个字节")
    private String name;

    @ApiModelProperty(value = "帐号 最大字符长度：128个字节")
    private String account;

    @ApiModelProperty(value = "帐号类型:1-moid；4-非系统邮箱；5-e164号码；6-电话；7-ip地址；8-别名@ip(监控前端)；")
    private Integer accountType;

    @ApiModelProperty(value = "座机 最大字符长度：地区区号4位-字符电话16位-字符分机10位字符")
    private String telephone;

    @ApiModelProperty(value = "手机 最大字符长度：15个字节")
    private String mobile;

}
