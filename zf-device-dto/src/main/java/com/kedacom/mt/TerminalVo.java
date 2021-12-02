package com.kedacom.mt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/30
 */
@Data
public class TerminalVo {

    @ApiModelProperty("终端标识")
    private Integer id;

    @NotNull(message = "名称不能为空")
    @ApiModelProperty("终端名称")
    @Size(max = 16,message = "您输入的名称超16位请检查")
    private String name;

    @NotNull(message = "IP不能为空")
    @ApiModelProperty("终端IP")
    private String ip;

    @ApiModelProperty("终端端口")
    @Min(value = 0, message = "设备端口号参数不正确")
    @Max(value = 65536, message = "设备端口号参数不正确")
    private Integer port;

    @NotNull(message = "账号不能为空")
    @ApiModelProperty("终端登录账号")
    @Size(max = 24,message = "您输入的账号超24位请检查")
    private String username;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty("终端登录密码")
    @Size(max = 24,message = "您输入的密码超24位请检查")
    private String password;

    @ApiModelProperty("登录终端成功后返回标识")
    private Integer mtid;

    @NotNull(message = "终端版本不能为空")
    @ApiModelProperty("会议终端版本：（三代高清版本填:2  5.0版本填：11）")
    private Integer devtype;

    @ApiModelProperty("会议终端e164号")
    private String e164;

    @ApiModelProperty("会议终端设备类型")
    private String mtname;

    @ApiModelProperty("链接状态: 0：未连接 1：已连接")
    private Integer status;

    @ApiModelProperty("UPU名称")
    private String upuname;

}
