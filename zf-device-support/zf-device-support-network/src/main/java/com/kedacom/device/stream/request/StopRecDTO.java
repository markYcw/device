package com.kedacom.device.stream.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:44
 */
@Data
@ApiModel("停止录像业务交互参数")
public class StopRecDTO extends StreamMediaDTO {

    private static final String COMMAND = "stoprec";

    @ApiModelProperty("登录token")
    private String account_token;

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    private String request_id;

    @ApiModelProperty("录像ID")
    private String recordId;

    @Override
    String getCommand() {
        return COMMAND;
    }

}
