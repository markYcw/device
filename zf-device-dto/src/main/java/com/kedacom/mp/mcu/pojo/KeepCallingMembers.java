package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/26 10:29
 * @description
 */
@ApiModel("追呼成员数组")
@Data
public class KeepCallingMembers {

    @NotBlank(message = "名称 不能为空")
    @ApiModelProperty(value = "名称 最大字符长度：128个字节",required = true)
    private String name;

    @NotBlank(message = "帐号 不能为空")
    @ApiModelProperty(value = "帐号 最大字符长度：128个字节",required = true)
    private String account;


    @ApiModelProperty(value = "帐号类型\n" +
            "1-moid；\n" +
            "4-非系统邮箱；\n" +
            "5-e164号；\n" +
            "6-电话；\n" +
            "7-ip地址；\n" +
            "8-别名@ip(监控前端)；",required = true)
    @NotNull(message = "帐号类型 不能为空")
    private Integer accountType;

}
