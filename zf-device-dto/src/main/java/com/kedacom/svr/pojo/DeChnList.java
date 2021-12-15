package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 14:15
 * @description 获取解码通道列表
 */
@Data
public class DeChnList {

    @ApiModelProperty("通道ID")
    private Integer chnId;

    @ApiModelProperty("通道状态 0：无效 1：离线 2：在线 3：录像中")
    private Integer state;

    @ApiModelProperty("通道别名")
    private String alias;

}
