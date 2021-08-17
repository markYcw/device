package com.kedacom.device.mp.mcu.request;

import com.kedacom.mp.mcu.pojo.Chns;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 13:52
 * @description 开始/停止上电视墙向中间件请求参数
 */
@Data
@ApiModel(value = "开始/停止上电视墙向中间件请求参数")
public class McuTvWallRequest implements Serializable {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：开始，1：停止 ", required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @NotBlank(message = "电视墙id不能为空")
    @ApiModelProperty(value = "电视墙id", required = true)
    private String tvWallId;

    @ApiModelProperty(value = "通道信息，开始时必填 ")
    private Chns chns;

}
