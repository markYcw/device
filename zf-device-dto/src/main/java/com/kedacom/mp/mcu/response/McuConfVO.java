package com.kedacom.mp.mcu.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/14 15:29
 * @description 创建/删除会议响应
 */
@Data
@ApiModel(description =  "创建/删除会议响应")
public class McuConfVO implements Serializable {

    @ApiModelProperty(value = "会议号码，创建时返回")
    private String confId;

}
