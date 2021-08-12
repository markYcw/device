package com.kedacom.meeting.mcu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author hxj
 * @Date: 2021/8/12 14:51
 * @Description 会议平台基本请求参数
 */
@Data
@ApiModel(value = "会议平台基本请求参数")
public class McuRequestDTO implements Serializable {

    @ApiModelProperty(value = "会议平台数据库id")
    private Long mcuId;

}
