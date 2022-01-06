package com.kedacom.svr.dto;

import com.kedacom.svr.pojo.PicChnVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 16:11
 * @description
 */
@Data
public class SetSvrComposePicVo extends SvrRequestDto {

    @NotNull(message = "画面合成风格不能为空")
    @ApiModelProperty(value = "画面合成风格 0：大画面 1：大小画面，小画面在右下方 2：2 等分 3：1 + 下 2 4：1 + 下 3 5：4 等分 6：1 + 右 3 7：1 + 右 4 8：1 + 5 9：1 + 7 10：9 等分 11：大小画面 11：大小画面 13：大小画面 14：1 + 右 2 ",required = true)
    private Integer mergeStyle;

    @NotNull(message = "拉伸方式不能为空")
    @ApiModelProperty(value = "拉伸方式 ,当自定义画面为标准风格时候有效 0：铺满所在区域 1：按原比例缩放 2：自适应",required = true)
    private Integer stretchStyle;

    @NotNull(message = "边框宽度不能为空")
    @ApiModelProperty(value = "边框宽度",required = true)
    private Integer borderWidth;

    @NotNull(message = "分辨率不能为空")
    @ApiModelProperty(value = "分辨率 0:无效 1:自动 2:176x144 3:352x288 4:704x288 5:704x576 6:88x72 7:640x480 8:320x240 9:1280x720" +
            "10: 1920x1080 11:704x400 12:800x600 13:1024x768 14:1280x1024 15:1600x1200 16:1280x960 17:720*576 18：2560*1440 19：3840*2160",required = true)
    private Integer resolution;

    @NotNull(message = "边框颜色R不能为空")
    @ApiModelProperty(value = "边框颜色R",required = true)
    private Integer borderColorRed;

    @NotNull(message = "边框颜色G不能为空")
    @ApiModelProperty(value = "边框颜色G",required = true)
    private Integer borderColorGreen;

    @NotBlank(message = "边框颜色B不能为空")
    @ApiModelProperty(value = "边框颜色B",required = true)
    private String borderColorBlue;

    @ApiModelProperty(value = "画面通道ID列表",required = true)
    private List<PicChnVo> picChnList;

}
