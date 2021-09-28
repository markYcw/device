package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/24 17:25
 * @description 画面合成成员列表
 */
@Data
@ApiModel(description = "画面合成成员列表")
public class Members {

    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称 最大字符长度：128个字节 仅当跟随类型为会控指定时才需要输入",required = true)
    private String name;

    @NotBlank(message = "帐号不能为空")
    @ApiModelProperty(value = "帐号 最大字符长度：128个字节 仅当跟随类型为会控指定时才需要输入",required = true)
    private String account;

    @NotNull(message = "帐号类型不能为空")
    @ApiModelProperty(value = "帐号类型",required = true)
    private Integer accountType;

    @NotNull(message = "跟随类型不能为空")
    @ApiModelProperty(value = "跟随类型 1-会控指定；2-发言人跟随；3-管理方跟随；4-会议轮询跟随；6-单通道轮询；7-内容共享跟随",required = true)
    private Integer memberType;

    @NotNull(message = "在画画合成中的位置不能为空")
    @ApiModelProperty(value = "在画画合成中的位置",required = true)
    private Integer chnIdx;

    @NotEmpty(message = "单通道轮询设置不能为空")
    @ApiModelProperty(value = "单通道轮询设置",required = true)
    private PollInfo poll;




}
