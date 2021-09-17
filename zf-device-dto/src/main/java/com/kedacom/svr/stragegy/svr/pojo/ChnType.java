package com.kedacom.svr.stragegy.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:13
 * @description
 */
@Data
public class ChnType {

    @ApiModelProperty("激励的通道")
    private Integer actChn;
}
