package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/15 17:00
 * @description 通道信息
 */
@Data
public class PicChn {

    @ApiModelProperty("通道ID")
    private Integer chnId;

    @ApiModelProperty("是否辅流 0：主流 1：辅流")
    private Integer isSec;

}
