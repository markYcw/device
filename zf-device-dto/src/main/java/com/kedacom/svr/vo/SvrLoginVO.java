package com.kedacom.svr.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ycw
 * @Date: 2021/09/06 14:29
 * @Description 登录成功响应
 */
@Data
@ApiModel(description =  "登录成功响应")
public class SvrLoginVO implements Serializable {

    @ApiModelProperty(value = "登录成功后会话id")
    private Integer ssid;

}
