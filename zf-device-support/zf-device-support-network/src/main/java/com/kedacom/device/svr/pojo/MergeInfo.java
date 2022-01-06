package com.kedacom.device.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 16:11
 * @description
 */
@Data
public class MergeInfo {

    @ApiModelProperty("画面合成风格 0：大画面 1：大小画面，小画面在右下方 2：2 等分 3：1 + 下 2 4：1 + 下 3 5：4 等分 6：1 + 右 3 7：1 + 右 4 8：1 + 5 9：1 + 7 10：9 等分 11：大小画面 11：大小画面 13：大小画面 14：1 + 右 2 ")
    private Integer mergeStyle;

    @ApiModelProperty("拉伸方式 ,当自定义画面为标准风格时候有效 0：铺满所在区域 1：按原比例缩放 2：自适应")
    private Integer stretchStyle;

    @ApiModelProperty("分辨率")
    private Integer resolution;

    @ApiModelProperty("边框宽度")
    private Integer borderWidth;

    @ApiModelProperty("边框颜色R")
    private Integer borderColorRed;

    @ApiModelProperty("边框颜色G")
    private Integer borderColorGreen;

    @ApiModelProperty("边框颜色B")
    private String borderColorBlue;

    @ApiModelProperty("画面通道ID列表")
    private List<PicChn> picChnList;


}
