package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/11
 */
@Data
public class VodStreamDetailResponseVo implements Serializable {

    @ApiModelProperty("流地址")
    private String url;

    @ApiModelProperty("开始时间")
    private String start;

    @ApiModelProperty("结束时间")
    private String end;

}
