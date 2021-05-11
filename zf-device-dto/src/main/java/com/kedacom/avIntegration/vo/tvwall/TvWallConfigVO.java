package com.kedacom.avIntegration.vo.tvwall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 15:07
 */
@Data
@ApiModel("设置大屏返回")
public class TvWallConfigVO implements Serializable {

    @ApiModelProperty(value = "大屏ID")
    private Integer id;

}
