package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 13:59
 * @description 发送短消息向中间件请求参数
 */
@Data
@ApiModel(description =  "发送短消息向中间件请求参数")
public class McuMessageRequest implements Serializable {

    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "消息内容,发送空消息即停止短消息 最大字符长度：1500个字节", required = true)
    private String message;

    @ApiModelProperty(value = "滚动次数 1-255 新版本终端255为无限轮询", required = true)
    private Integer rollNum;

    @ApiModelProperty(value = "滚动速度\n" +
            "1-慢速；\n" +
            "2-中速；\n" +
            "3-快速；", required = true)
    private Integer rollSpeed;

    @ApiModelProperty(value = "短消息类型\n" +
            "0-自右至左滚动；\n" +
            "1-翻页滚动；\n" +
            "2-全页滚动；", required = true)
    private Integer messageType;

    @ApiModelProperty(value = "终端列表", required = true)
    private List<String> mtInfos;

}
