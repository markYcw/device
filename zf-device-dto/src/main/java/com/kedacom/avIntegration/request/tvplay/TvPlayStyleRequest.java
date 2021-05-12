package com.kedacom.avIntegration.request.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 09:54
 */
@Data
@ApiModel("设置窗口风格入参")
public class TvPlayStyleRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填", required = true)
    private String token;

    @NotNull(message = "预案ID不能为空")
    @ApiModelProperty(value = "预案ID - 必填，预案的配置ID", required = true)
    private Integer schid;

    @NotNull(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 必填，预案所属的大屏ID", required = true)
    private Integer tvid;

    @NotNull(message = "窗口ID不能为空")
    @ApiModelProperty(value = "窗口ID - 必填", required = true)
    private Integer wndid;

    @NotNull(message = "画面风格不能为空")
    @ApiModelProperty(value = "画面风格 - 必填，1=一画面，4=四画面", required = true)
    private Integer style;

}
