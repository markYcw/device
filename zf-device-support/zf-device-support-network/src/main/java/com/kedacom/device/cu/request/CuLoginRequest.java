package com.kedacom.device.cu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ycw
 * @Date: 2021/11/01 14:22
 * @Description 登录cu向中间件请求参数
 */
@Data
@ApiModel(description =  "登录cu向中间件请求参数")
public class CuLoginRequest implements Serializable {

    /**
     * cuIP
     */
    @ApiModelProperty(value = "svrIP")
    private String ip;

    /**
     * cu端口
     */
    @ApiModelProperty(value = "端口，一般是9765")
    private Integer port;

    /**
     * type
     */
    @ApiModelProperty(value = "类型，0:1.0平台 1:2.0平台")
    private Integer type;

    /**
     * 登录cu用户名
     */
    @ApiModelProperty(value = "登录cu用户名")
    private String user;

    /**
     * 登录cu密码
     */
    @ApiModelProperty(value = "登录cu密码")
    private String password;

    /**
     * 主动上报url，用于推送消息
     */
    @ApiModelProperty(value = "主动上报url，用于推送消息")
    private String ntyUrl;

    /**
     * 心跳检测时间，单位分钟，默认为10
     */
    @ApiModelProperty(value = "心跳检测时间，单位分钟，默认为10 eg:10")
    private Integer hbTime;


}
