package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 13:21
 * @description 录像终端数组
 */
@Data
@ApiModel(description =  "录像终端")
public class RecMember implements Serializable {

    @NotBlank(message = "轮询终端不能为空")
    @ApiModelProperty(value = "轮询终端 最大字符长度：48个字节",required = true)
    private String mtId;

}
