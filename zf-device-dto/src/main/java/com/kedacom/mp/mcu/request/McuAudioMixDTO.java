package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 11:21
 * @description 开始/停止混音入参
 */
@Data
@ApiModel(description =  "开始/停止混音入参")
public class McuAudioMixDTO extends McuRequestDTO {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：开始，1：停止", required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "混音模式1-智能混音；2-定制混音；")
    private Integer mode;

    @ApiModelProperty(value = "混音成员，终端ID列表，开始时必填")
    private List<String> mtInfos;
}
