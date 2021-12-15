package com.kedacom.device.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 17:28
 * @description 编码通道列表信息
 */
@Data
public class EnChnList {

    @ApiModelProperty("通道ID")
    private Integer chnId;

    @ApiModelProperty("通道状态 0：无效 1：离线 2：在线 3：录像中")
    private Integer state;

    @ApiModelProperty("是否支持辅流 0：不支持 1：支持")
    private Integer supportSec;

    @ApiModelProperty("通道别名")
    private String alias;

}
