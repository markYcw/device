package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 13:32
 * @description 轮询信息
 */
@Data
@ApiModel(value = "轮询信息")
public class Poll implements Serializable {

    @ApiModelProperty(value = "轮询次数")
    private Integer num;

    @ApiModelProperty(value = "轮询间隔（秒）")
    private Integer keepTime;

    @ApiModelProperty(value = "轮询方式\n" +
            "1-仅图像；\n" +
            "3-音视频轮询；")
    private Integer mode;

    @ApiModelProperty(value = "轮询成员")
    private List<RecMember> members;

}
