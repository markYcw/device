package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author hxj
 * @date: 2021/8/12 19:04
 * @description 获取会议列表入参
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "获取会议列表入参")
public class McuConfsDTO extends McuRequestDTO {

    @NotNull(message = "本次开始查询索引不能为空")
    @ApiModelProperty(value = "本次开始查询索引", required = true)
    private Integer start;

    @NotNull(message = "本次查询总数不能为空")
    @ApiModelProperty(value = "本次查询总数", required = true)
    private Integer count;
}
