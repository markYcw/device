package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 11:21
 * @description 开始/停止混音向中间件请求参数
 */
@Data
@ApiModel(description =  "开始/停止混音向中间件请求参数")
public class McuAudioMixRequest implements Serializable {

    @ApiModelProperty(value = "0：开始，1：停止", required = true)
    private Integer type;

    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "混音模式\n" +
            "1-智能混音；\n" +
            "2-定制混音；")
    private Integer mode;

    @ApiModelProperty(value = "混音成员，开始时必填")
    private List<String> mtInfos;

}
