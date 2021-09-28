package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/24 17:17
 * @description 画面合成参数
 */
@Data
public class MtNameStyle {

    @NotNull(message = "台标字体大小不能为空")
    @ApiModelProperty(value = "台标字体大小",required = true)
    private Integer fontSize;

    @NotBlank(message = "台标字体三原色不能为空")
    @ApiModelProperty(value = "台标字体三原色#RGB格式，十六进制表示，默认为：#FFFFFF白色",required = true)
    private String fontColor;

    @NotNull(message = "台标显示位置不能为空")
    @ApiModelProperty(value = "台标显示位置，默认为1 0-左上角； 1-左下角； 2-右上角；3-右下角；4-底部中间；",required = true)
    private Integer position;

    @NotNull(message = "终端呼叫码率不能为空")
    @ApiModelProperty(value = "终端呼叫码率，不可超过会议码率，默认为会议码率",required = true)
    private Integer bitrate;

    @NotNull(message = "呼叫协议不能为空")
    @ApiModelProperty(value = "呼叫协议:0-H323；1-SIP；",required = true)
    private Integer protocol;

}
