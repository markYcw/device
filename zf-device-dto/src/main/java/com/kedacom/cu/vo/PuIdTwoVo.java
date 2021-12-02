package com.kedacom.cu.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 */
@Data
public class PuIdTwoVo {

    @ApiModelProperty("平台域")
    private String domain;

    @ApiModelProperty("设备puId")
    private String puId;

    @ApiModelProperty("通道号")
    private Integer chn;


}
