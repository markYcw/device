package com.kedacom.svr.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/10 13:35
 * @description ptz控制
 */
@Data
public class PtzCtrlRequestVo extends SvrRequestDto{

    @NotNull(message = "通道ID不能为空")
    @ApiModelProperty(value = "通道ID",required = true)
    @JSONField(name = "chnId")
    private Integer chnid;

    @NotNull(message = "控制命令不能为空")
    @ApiModelProperty(value = "控制命令 控制指令解释请参考PtzCmd枚举类",required = true)
    private Integer cmd;

    @ApiModelProperty("控制指令解释PtzCmd枚举类，此字段为上一个字段起解释说明服务，不必传 前端不需要理会这个字段")
    private PtzCmd ptzCmd;

    @NotBlank(message = "param1不能为空")
    @ApiModelProperty(value = "命令参数 请参考PtzCmd枚举类",required = true)
    private String param1;

    @NotBlank(message = "param2不能为空")
    @ApiModelProperty(value = "命令参数 请参考PtzCmd枚举类",required = true)
    private String param2;

    @NotBlank(message = "param3不能为空")
    @ApiModelProperty(value = "命令参数 请参考PtzCmd枚举类",required = true)
    private String param3;

    @NotBlank(message = "param4不能为空")
    @ApiModelProperty(value = "命令参数 请参考PtzCmd枚举类",required = true)
    private String param4;

}
