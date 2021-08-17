package com.kedacom.device.mp.mcu.request;

import com.kedacom.mp.mcu.pojo.RecParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 11:26
 * @description 开始/暂停/恢复/停止录像向中间件请求参数
 */
@Data
@ApiModel(value = "开始/暂停/恢复/停止录像向中间件请求参数")
public class McuRecRequest implements Serializable {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：开始，1：暂停，2：恢复，3：停止", required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "录像id，非开始时必填")
    private String recId;

    @ApiModelProperty(value = "录像参数，开始时必填")
    private RecParam recParam;




}
