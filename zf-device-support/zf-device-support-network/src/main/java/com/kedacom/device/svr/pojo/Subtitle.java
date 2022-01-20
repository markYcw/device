package com.kedacom.device.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 18:53
 * @description 自定义叠加
 */
@Data
public class Subtitle {

    @ApiModelProperty("是否显示")
    private Integer showOsd;

    @ApiModelProperty("叠加位置x")
    private Integer xPos;

    @ApiModelProperty("叠加位置y")
    private Integer yPos;

    @ApiModelProperty("自定义内容")
    private String content;


}
