package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 13:59
 * @description 发送短消息入参
 */
@Data
@ApiModel(description =  "发送短消息入参")
public class McuMessageDTO extends McuRequestDTO {

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @NotBlank(message = "消息内容不能为空")
    @ApiModelProperty(value = "消息内容,发送空消息即停止短消息 最大字符长度：1500个字节", required = true)
    private String message;

    @NotNull(message = "滚动次数不能为空")
    @ApiModelProperty(value = "滚动次数 1-255 新版本终端255为无限轮询", required = true)
    private Integer rollNum;

    @NotNull(message = "滚动速度不能为空")
    @ApiModelProperty(value = "滚动速度 1-慢速； 2-中速；3-快速；", required = true)
    private Integer rollSpeed;

    @NotNull(message = "短消息类型不能为空")
    @ApiModelProperty(value = "短消息类型 0-自右至左滚动；1-翻页滚动；2-全页滚动；", required = true)
    private Integer messageType;

    @NotEmpty(message = "终端id列表不能为空")
    @ApiModelProperty(value = "终端id列表", required = true)
    private List<String> mtInfos;

}
