package com.kedacom.core;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/6/1 13:54
 */
@Data
public class AcceptUrlListen implements Serializable {

    @ApiModelProperty("主题")
    private String topic;

    @ApiModelProperty("接受消息URL")
    private String acceptUrl;

}
