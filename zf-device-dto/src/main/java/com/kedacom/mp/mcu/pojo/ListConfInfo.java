package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/16 11:01
 * @description 会议列表
 */
@Data
@ApiModel(description =  "会议列表")
public class ListConfInfo implements Serializable {

    @ApiModelProperty(value = "会议号码")
    private String confId;

    @ApiModelProperty(value = "名称")
    private String name;

}
