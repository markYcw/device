package com.kedacom.avIntegration.request.tvwall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 11:05
 */
@Data
@ApiModel("获取大屏布局入参")
public class TvWallLayoutRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填", required = true)
    private String token;

    @ApiModelProperty(value = "大屏ID - 不填或填0查询全部")
    private Integer tvid;

}
