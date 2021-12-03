package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 15:40
 * @description 通道信息，添加时必填
 */
@Data
public class DeChnInfo {

    @NotBlank(message = "设备guid不能为空")
    @ApiModelProperty(value = "设备guid",required = true)
    private String guid;

    @NotBlank(message = "通道别名不能为空")
    @ApiModelProperty(value = "通道别名",required = true)
    private String name;

    @NotBlank(message = "设备类型名称不能为空")
    @ApiModelProperty(value = "设备类型名称",required = true)
    private String typeName;

    @ApiModelProperty("设备IP 2.0必填")
    private String ip;

    @ApiModelProperty("传输协议 0：tcp 1：udp 3.0必填")
    private String transMode;

    @ApiModelProperty("连接模式: 0：被动 1：主动 3.0必填 ")
    private String connectMode;

    @ApiModelProperty("通信端口 3.0必填")
    private String port;

}
