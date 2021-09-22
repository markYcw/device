package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 15:52
 * @description onvif信息，protoType为1时生效
 */
@Data
public class OnVifInfo {

    @ApiModelProperty("onvif设备uuid")
    private String uuid;

    @ApiModelProperty("onvif设备xaddr")
    private String xaddr;

    @ApiModelProperty("onvif设备登录用户名")
    private String userName;

    @ApiModelProperty("onvif设备登录密码")
    private String passWord;


}
