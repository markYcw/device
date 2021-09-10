package com.kedacom.svr.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 10:24
 * @description 获取编码器的预置位
 */
@Data
public class CpResetVo implements Serializable {

    @ApiModelProperty("预置位1-255")
    private Integer preset;

}
