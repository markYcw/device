package com.kedacom.cu.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *获取多视图设备树响应体
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 10:18
 * @description
 */
@Data
public class Subscribe implements Serializable {

    @ApiModelProperty("在线状态 0：不订阅 1：订阅")
    @NotNull
    private Integer online;

    @ApiModelProperty("告警 0：不订阅 1：订阅")
    @NotNull
    private Integer alarm;

    @ApiModelProperty("通道状态0：不订阅1：订阅")
    @NotBlank
    private Integer chn;

    @ApiModelProperty("gps：不订阅1：订阅")
    @NotNull
    private Integer gps;

    @ApiModelProperty("电视墙 0：不订阅 1：订阅")
    @NotNull
    private Integer tvwall;

    @ApiModelProperty("录像状态 0：不订阅 1：订阅")
    @NotNull
    private Integer rec;

    @ApiModelProperty("透明通道状态： 0:不订阅1：订阅")
    @NotNull
    private Integer transdata;

}
