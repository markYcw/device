package com.kedacom.acl.network.data.streamMeida;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 19:34
 */
@Data
@ApiModel(description = "停止录像业务交互参数")
public class StopRecRequestDTO implements Serializable {

    @ApiModelProperty("登录token")
    private String account_token;

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    private String request_id;

    @ApiModelProperty("录像ID")
    private String recordId;

}
