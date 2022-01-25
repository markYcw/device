package com.kedacom.svr.stragegy.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:10
 * @description 通知内容
 */
@Data
public class AudioActInfo {

    @ApiModelProperty("通道类型 0:未知 1:mac in音频通道 2:line in音频通道 3:远程点 in音频通道 4:编码 in音频通道 5:播放 in音频通道")
    private List<ChnType> chnType;

    @ApiModelProperty("激励的通道")
    private Integer actChn;

}
