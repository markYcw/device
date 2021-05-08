package com.kedacom.avIntegration.response.tvwall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 15:07
 */
@Data
@ApiModel("设置大屏应答")
public class TvWallConfigResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

    @ApiModelProperty(value = "大屏ID")
    private Integer id;

}
