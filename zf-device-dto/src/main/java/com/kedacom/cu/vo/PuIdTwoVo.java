package com.kedacom.cu.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 */
@Data
public class PuIdTwoVo {

    @ApiModelProperty("视频源号")
    private String src;

    @ApiModelProperty("设备puId")
    private String puId;

    @ApiModelProperty("通道号")
    private Integer chn;


}
