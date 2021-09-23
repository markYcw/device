package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.pojo.CdConfInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author hxj
 * @date: 2021/8/14 15:29
 * @description 创建/删除会议入参
 */
@Data
@ApiModel(description =  "创建/删除会议入参")
public class McuConfDTO extends McuRequestDTO {

    @NotNull(message = "type不能为空")
    @ApiModelProperty(value = "0：创建即时会议;1：创建会议模板;2：结束即时会议;3：删除会议模板",required = true)
    private Integer type;

    @ApiModelProperty(value = "会议号码，除type 是0外必填,最大字符长度：48个字节")
    private String confId;

    @ApiModelProperty(value = "会议信息，创建时必填")
    private CdConfInfo confInfo;

}
