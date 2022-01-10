package com.kedacom.device.vs.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/1/10 9:52
 * @description
 */
@Data
public class VsLoginResponse extends VsResponse{

    @ApiModelProperty(value = "登录成功后会话id")
    private Integer ssid;
}
