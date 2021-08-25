package com.kedacom.msp.request.tvwall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 14:35
 */
@Data
@ApiModel(description = "查询大屏通道绑定入参")
public class TvWallQueryPipelineRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填", required = true)
    private String token;

    @NotNull(message = "大屏ID不能为空")
    @ApiModelProperty(value = "必填 - 大屏ID", required = true)
    private Integer id;

}
