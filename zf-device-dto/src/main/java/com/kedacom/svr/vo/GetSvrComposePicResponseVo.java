package com.kedacom.svr.vo;

import com.kedacom.svr.pojo.PicChnVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/1/6 17:03
 * @description
 */
@Data
public class GetSvrComposePicResponseVo {
    @ApiModelProperty(value = "分辨率 0:无效 1:自动 2:176x144 3:352x288 4:704x288 5:720x576 6:88x72 7:640x480 8:320x240 9:1280x720" +
            "10: 1920x1080 11:704x400 12:800x600 13:1024x768 14:1280x1024 15:1600x1200 16:1280x960 17:720*576 18：2560*1440 19：3840*2160")
    private Integer videoresolution;

    @ApiModelProperty(value = "边框宽度")
    private Integer borderwidth;

    @ApiModelProperty(value = "自定义画面风格所使用的基础画面风格。 SVR2816 1.0不支持  0：大画面 1：大小画面，小画面在右下方 2：2 等分 3：1 + 下 2 4：1 + 下 3 5：4 等分 6：1 + 右 3 7：1 + 右 4 8：1 + 5 9：1 + 7 10：9 等分 11：大小画面，小画面在左下 12：大小画面，小画面在左上 13：大小画面，小画面在右上 14：1 + 右 2")
    private Integer mergestyle;

    @ApiModelProperty("拉伸方式 ,当自定义画面为标准风格时候有效 0：无效 1：按原比例缩放 2：自适应 3:铺满所在区域（仅3.0支持）")
    private Integer stretchStyle;

    @ApiModelProperty("边框颜色R")
    private Integer borderColorRed;

    @ApiModelProperty("边框颜色G")
    private Integer borderColorGreen;

    @ApiModelProperty("边框颜色B")
    private String borderColorBlue;

    @ApiModelProperty("画面通道列表")
    private List<PicChnVo> picChns;
}
