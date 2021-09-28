package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/26 10:20
 * @description
 */
@ApiModel(description = "vip成员列表")
@Data
public class Vips {


    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称 最大字符长度：128个字节",required = true)
    private String name;

    @ApiModelProperty(value = "帐号 最大字符长度：128个字节",required = true)
    @NotBlank(message = "帐号 不能为空")
    private String account;

    @NotNull(message = "帐号类型不能为空")
    @ApiModelProperty(value = "帐号类型 1-moid； 4-非系统邮箱； 5-e164号；6-电话； 7-ip地址；8-别名@ip(监控前端)",required = true)
    private Integer accountType;


}
