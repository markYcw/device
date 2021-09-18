package com.kedacom.device.svr.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ycw
 * @Date: 2021/09/06 14:22
 * @Description 登录SVR向中间件请求参数
 */
@Data
@ApiModel(description =  "登录SVR向中间件请求参数")
public class SvrLoginRequest implements Serializable {

    /**
     * svrIP
     */
    @ApiModelProperty(value = "svrIP")
    private String ip;
    /**
     * svr端口
     */
    @ApiModelProperty(value = "端口，一般是9765")
    private Integer port;
    /**
     * 登录svr用户名
     */
    @ApiModelProperty(value = "登录svr用户名")
    private String user;
    /**
     * 登录svr密码
     */
    @ApiModelProperty(value = "登录svr密码")
    private String password;

    /**
     * 主动上报url，用于推送消息
     */
    @ApiModelProperty(value = "主动上报url，用于推送消息")
    private String ntyUrl;

    /**
     * svr的websocket端口，一般是9766
     */
    @ApiModelProperty(value = "svr的websocket端口，一般是9766")
    private Integer webPort;

    /**
     * 心跳检测时间，单位分钟，默认为10
     */
    @ApiModelProperty(value = "心跳检测时间，单位分钟，默认为10 eg:10")
    private Integer hbTime;


}
