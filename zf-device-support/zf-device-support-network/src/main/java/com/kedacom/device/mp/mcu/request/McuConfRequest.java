package com.kedacom.device.mp.mcu.request;

import com.kedacom.mp.mcu.pojo.CdConfInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 10:23
 * @description 创建/删除会议向中间件请求参数
 */
@Data
@ApiModel(description =  "创建/删除会议向中间件请求参数")
public class McuConfRequest implements Serializable {

    @ApiModelProperty(value = "0：创建即时会议;1：创建会议模板;2：结束即时会议;3：删除会议模板")
    private Integer type;

    @ApiModelProperty(value = "会议号码，除type 是0外必填,最大字符长度：48个字节")
    private String confId;

    @ApiModelProperty(value = "会议信息，创建时必填")
    private CdConfInfo confInfo;

}
