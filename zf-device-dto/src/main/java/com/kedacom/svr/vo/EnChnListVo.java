package com.kedacom.svr.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 9:48
 * @description获取编解码通道列表返回
 */
@Data
public class EnChnListVo implements Serializable {

    @ApiModelProperty("通道ID")
    private Integer chnId;

    @ApiModelProperty("通道状态 0：无效 1：离线 2：在线 3：录像中")
    private Integer state;

    @ApiModelProperty("是否支持辅流 0：不支持 1：支持")
    private Integer supportSec;

    @ApiModelProperty("通道别名")
    private String alias;

}
