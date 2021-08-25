package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/19 15:10
 * @description 选看源
 */
@Data
@ApiModel(description =  "选看源")
public class MtSrc implements Serializable {

    @ApiModelProperty(value = "选看源类型\n" +
            "1-终端；\n" +
            "2-画面合成；")
    private Integer srcType;

    @ApiModelProperty(value = "源终端号(仅选看源类型为终端有效) 最大字符长度：48个字节")
    private String mtId;

    @ApiModelProperty(value = "目的终端号(选看源类型为画面合成时, 必须为主席终端号) 最大字符长度：48个字节")
    private String mtDst;

}
