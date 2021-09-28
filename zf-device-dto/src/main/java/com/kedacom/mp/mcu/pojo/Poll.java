package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 13:32
 * @description 轮询信息
 */
@Data
@ApiModel(description =  "轮询信息")
public class Poll implements Serializable {

    @NotNull(message = "轮询次数不能为空")
    @ApiModelProperty(value = "轮询次数",required = true)
    private Integer num;

    @NotNull(message = "轮询次数不能为空")
    @ApiModelProperty(value = "轮询间隔（秒）",required = true)
    private Integer keepTime;

    @NotNull(message = "轮询次数不能为空")
    @ApiModelProperty(value = "轮询方式1-仅图像；3-音视频轮询；",required = true)
    private Integer mode;

    @NotEmpty(message = "轮询成员不能为空")
    @ApiModelProperty(value = "轮询成员",required = true)
    private List<RecMember> members;

}
