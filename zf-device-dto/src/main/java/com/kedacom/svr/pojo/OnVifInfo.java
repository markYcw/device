package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 15:52
 * @description onvif信息，protoType为1时生效
 */
@Data
public class OnVifInfo {

    @NotBlank(message = "onvif设备uuid不能为空")
    @ApiModelProperty(value = "onvif设备uuid",required = true)
    private String uuid;

    @NotBlank(message = "onvif设备xaddr不能为空")
    @ApiModelProperty(value = "onvif设备xaddr",required = true)
    private String xaddr;

    @NotBlank(message = "onvif设备登录用户名不能为空")
    @ApiModelProperty(value = "onvif设备登录用户名",required = true)
    private String userName;

    @NotBlank(message = "onvif设备登录密码不能为空")
    @ApiModelProperty(value = "onvif设备登录密码",required = true)
    private String passWord;


}
