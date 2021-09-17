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

    @ApiModelProperty("仓门状态 0：无效状态 1：仓门关闭，无盘片状态 2：仓门打开 3：仓门关闭，准备就绪,就绪之后才能开始刻录 4:仓门关闭，未准备就绪")
    private Integer doorState;

    @ApiModelProperty("光盘状态 0：无效盘 1：空白盘，可以第一次刻录 2：未封口盘，可以追加刻录 3：封口盘，除DVD+RW类型和DVD-RW的受限覆盖模式类型可以追加刻录外，其他类型盘不可刻录 4:其他未知，不可刻录")
    private Integer discState;
}
