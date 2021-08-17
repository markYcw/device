package com.kedacom.mp.mcu.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author hxj
 * @Date: 2021/8/12 14:29
 * @Description 登录成功响应
 */
@Data
@ApiModel(value = "登录成功响应")
public class McuLoginVO implements Serializable {

    @ApiModelProperty(value = "登录成功后会话id")
    private String ssid;

}
