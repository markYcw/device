package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author ycw
 * @date: 2021/8/17 11:21
 * @description 获取录像列表
 */
@Data
@ApiModel(description =  "获取录像列表")
@ToString(callSuper = true)
public class RecsDTO extends McuRequestDTO{

    @NotNull(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;


}
