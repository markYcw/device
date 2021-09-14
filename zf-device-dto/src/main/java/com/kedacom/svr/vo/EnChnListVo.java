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

    @ApiModelProperty("设备ID")
    private Integer devId;

    @ApiModelProperty("0：无效设备 1：编码器 2：解码器 3：增加支持平台设备,针对版本Beta2 4：多媒体主机,包括SVR29ES 和 SVR27ES 5：SDI编码卡 6：2726-VIN0 VIN1")
    private Integer chnType;

    @ApiModelProperty("通道状态 0：无效 1：离线 2：在线 3：录像中")
    private Integer chnState;

    @ApiModelProperty("是否支持辅流 0：不支持 1：支持")
    private Integer supportSec;

    @ApiModelProperty("通道别名")
    private String alias;

}
