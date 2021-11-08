package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/4 14:51
 * @description
 */
@ApiModel(description = "录像信息")
@Data
public class Recs {

    @ApiModelProperty("录像id")
    private String id;

    @ApiModelProperty("录像类型")
    private Integer type;

    @ApiModelProperty("录像会话")
    private Integer session;

    @ApiModelProperty("开始时间")
    private String start;

    @ApiModelProperty("结束时间")
    private String end;

}
