package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 13:31
 * @description 选看参数
 */
@Data
@ApiModel(description =  "选看参数")
public class Specific implements Serializable {

    @ApiModelProperty(value = "选看类型1-指定； 2-发言人跟随；3-主席跟随；4-会议轮询跟随；6-选看画面合成；7-批量轮询；10-选看双流；",required = true)
    @NotBlank(message = "选看类型不能为空")
    private String memberType;

    @NotBlank(message = "选看终端id不能为空")
    @ApiModelProperty(value = "选看终端id，仅member_type为 1-指定 时生效 最大字符长度：48个字节",required = true)
    private String mtId;

    @NotBlank(message = "选看画面合成id不能为空")
    @ApiModelProperty(value = "选看画面合成id，仅member_type为 6-选看画面合成 时生效 最大字符长度：48个字节",required = true)
    private String vmpId;

}
