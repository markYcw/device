package com.kedacom.device.vs.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class VsLoginRequest implements Serializable {


    @ApiModelProperty("主动上报的url")
    private String ntyUrl;

    /**
     * VRS的IP
     */
    @ApiModelProperty("终端IP")
    private String ip;

    /**
     * VRS登录账号
     */
    @ApiModelProperty("终端登录账号")
    @JSONField(name = "user")
    private String username;

    /**
     * VRS登录密码
     */
    @ApiModelProperty("终端登录密码")
    @JSONField(name = "passWord")
    private String password;

    /**
     * VRS版本
     */
    @ApiModelProperty("终端版本")
    @JSONField(name = "devType")
    private Integer version;



}
