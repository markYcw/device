package com.kedacom.svr.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * SVR能力集响应体
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 10:18
 * @description
 */
@Data
public class SvrCapVo implements Serializable {

    @ApiModelProperty("支持最大编码通道数")
    private Integer maxEncNum;

    @ApiModelProperty("支持最大解码通道数")
    private Integer maxDecNum;

}
