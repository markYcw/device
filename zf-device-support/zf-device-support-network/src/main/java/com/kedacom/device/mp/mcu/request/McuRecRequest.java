package com.kedacom.device.mp.mcu.request;

import com.kedacom.mp.mcu.pojo.RecParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 11:26
 * @description 开始/暂停/恢复/停止录像向中间件请求参数
 */
@Data
@ApiModel(description =  "开始/暂停/恢复/停止录像向中间件请求参数")
public class McuRecRequest implements Serializable {

    @ApiModelProperty(value = "0：开始，1：暂停，2：恢复，3：停止", required = true)
    private Integer type;

    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "录像id，非开始时必填")
    private String recId;

    @ApiModelProperty(value = "录像模式\n" +
            "1-录像；\n" +
            "2-直播；\n" +
            "3-录像+直播；")
    private Integer recorderMode;


    @ApiModelProperty(value = "录像参数，开始时必填")
    private RecParam recParam;




}
