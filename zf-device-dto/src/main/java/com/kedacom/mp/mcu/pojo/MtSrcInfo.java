package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/23 14:38
 * @description 选看源，开始时必填
 */
@Data
public class MtSrcInfo {

    @NotNull(message = "选看源类型不能为空")
    @ApiModelProperty(value = "选看源类型 1-终端； 2-画面合成；")
    private Integer srcType;

    @NotNull(message = "源终端号")
    @ApiModelProperty(value = "源终端号(仅选看源类型为终端有效) 最大字符长度：48个字节")
    private String mtId;

    @NotNull(message = "目的终端号")
    @ApiModelProperty(value = "目的终端号(选看源类型为画面合成时, 必须为主席终端号) 最大字符长度：48个字节")
    private String mtDst;

}
