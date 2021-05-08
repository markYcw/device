package com.kedacom.avIntegration.request.decoder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 17:48
 */
@Data
@ApiModel(value = "配置解码通道字幕信息入参")
public class OsdConfigRequest implements Serializable {

    @NotEmpty(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @NotEmpty(message = "解码通道ID不能为空")
    @ApiModelProperty(value = "解码通道ID - 必填，统一设备服务分配国标ID")
    private Integer chnid;

    @ApiModelProperty(value = "字幕参数")
    private List<Subtitle> osdls;

    @Data
    class Subtitle {

        @ApiModelProperty(value = "画面编号 - 编号从0开始，0=第一画面")
        private Integer index;

        @NotEmpty(message = "字幕的内容不能为空")
        @ApiModelProperty(value = "字幕的内容 - 必填")
        private Integer content;

        @ApiModelProperty(value = "字幕的内容")
        private Position position;

        @ApiModelProperty(value = "字体信息")
        private FontInfo fontinfo;

    }

    @Data
    class Position {

        @NotEmpty(message = "字幕显示位置X坐标不能为空")
        @ApiModelProperty(value = "字幕显示位置X坐标 - 必填，单位：像素")
        private Integer left;

        @NotEmpty(message = "字幕显示位置Y坐标不能为空")
        @ApiModelProperty(value = "字幕显示位置Y坐标 - 必填，单位：像素")
        private Integer top;

        @NotEmpty(message = "字幕宽度不能为空")
        @ApiModelProperty(value = "字幕宽度 - 必填，单位：像素")
        private Integer width;

        @NotEmpty(message = "字幕高度不能为空")
        @ApiModelProperty(value = "字幕高度 - 必填，单位：像素")
        private Integer height;

    }

    @Data
    class FontInfo {

        @NotEmpty(message = "字体大小不能为空")
        @ApiModelProperty(value = "字体大小 - 必填，单位：像素")
        private Integer size;

        @NotEmpty(message = "字体颜色G不能为空")
        @ApiModelProperty(value = "字体颜色G - 必填")
        private Integer green;

        @NotEmpty(message = "字体颜色R不能为空")
        @ApiModelProperty(value = "字体颜色R - 必填")
        private Integer red;

        @NotEmpty(message = "字体颜色B不能为空")
        @ApiModelProperty(value = "字体颜色B - 必填")
        private Integer blue;

    }

}
