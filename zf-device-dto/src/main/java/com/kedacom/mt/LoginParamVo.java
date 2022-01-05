package com.kedacom.mt;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/1
 */
@Data
public class LoginParamVo implements Serializable {

    @ApiModelProperty("终端ip")
    private String ip;

    @ApiModelProperty("终端端口，默认:60000")
    private Integer port = 60000;

    @ApiModelProperty("用户名")
    private String user;

    @ApiModelProperty("密码")
    private String passWord;

    @ApiModelProperty("终端类型（版本）0：3.0，1：5.0")
    private Integer devType;

    @ApiModelProperty("主动上报的url")
    private String ntyUrl;

    @ApiModelProperty("心跳检测时间，单位分钟，默认为10")
    private Integer hbTime = 10;

    @ApiModelProperty("使用模式，一般不用填，默认为0")
    private Integer useMode = 0;

}
