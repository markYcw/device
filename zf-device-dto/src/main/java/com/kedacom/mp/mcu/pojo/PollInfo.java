package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/26 10:07
 * @description 单通道轮询设置
 */
@ApiModel(description = "单通道轮询设置")
@Data
public class PollInfo {

    @NotNull(message = "轮询次数不能为空")
    @ApiModelProperty(value = "轮询次数",required = true)
    private Integer num;

    @NotNull(message = "轮询次数不能为空")
    @ApiModelProperty(value = "轮询间隔（秒）",required = true)
    private Integer keepTime;

    @NotNull(message = "轮询次数不能为空")
    @ApiModelProperty(value = "轮询方式 1-仅图像；3-音视频轮询；",required = true)
    private Integer mode;

    @NotEmpty(message = "轮询成员列表不能为空")
    @ApiModelProperty(value = "轮询成员列表",required = true)
    private List<PollMember> members;

}
