package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 18:53
 * @description 温湿度叠加
 */
@Data
public class ChnList {

    @ApiModelProperty(value = "通道ID",required = true)
    private Integer chnId;

    @ApiModelProperty(value = "通道状态 0：无效 1：离线 2：在线 3：录像中")
    private Integer state;

    @ApiModelProperty(value = "通道别名",required = true)
    private String alias;

    @ApiModelProperty(value = "url")
    private String url;

}
