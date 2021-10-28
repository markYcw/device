package com.kedacom.device.mp.mcu.request;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author hxj
 * @Date: 2021/8/12 14:22
 * @Description 会议平台登录向中间件请求参数
 */
@Data
@ApiModel(description =  "会议平台登录向中间件请求参数")
public class McuLoginRequest implements Serializable {

    /**
     * 会议平台IP
     */
    @ApiModelProperty(value = "会议平台IP")
    private String ip;
    /**
     * 会议平台端口
     */
    @ApiModelProperty(value = "会议平台端口")
    private Integer port;

    /**
     * 平台类型
     */
    @ApiModelProperty(value = "平台类型 0:4.7 1:5.0")
    private Integer type;

    /**
     * 登录会议平台用户名
     */
    @ApiModelProperty(value = "登录会议平台用户名")
    private String user;
    /**
     * 登录会议平台密码
     */
    @ApiModelProperty(value = "登录会议平台密码")
    private String password;
    /**
     * 获取token使用
     */
    @ApiModelProperty(value = "获取token使用")
    @JSONField(name = "key")
    private String sdkkey;
    /**
     * 获取token使用
     */
    @ApiModelProperty(value = "获取token使用")
    @JSONField(name = "secret")
    private String sdksecret;
    /**
     * 主动上报url，用于推送消息
     */
    @ApiModelProperty(value = "主动上报url，用于推送消息")
    private String ntyUrl;

}
