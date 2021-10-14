package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.pojo.Chns;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hxj
 * @date: 2021/8/17 13:52
 * @description 开始/停止上电视墙入参
 */
@Data
@ApiModel(description =  "开始/停止上电视墙入参")
public class McuTvWallDTO extends McuRequestDTO {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：开始，1：停止 ", required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @NotBlank(message = "电视墙id不能为空")
    @ApiModelProperty(value = "电视墙id", required = true)
    private String hduId;

    @ApiModelProperty(value = "电视墙信息，开始时必填 ")
    private Chns hdu;

}
