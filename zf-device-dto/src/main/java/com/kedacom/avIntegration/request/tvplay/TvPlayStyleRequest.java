package com.kedacom.avIntegration.request.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 09:54
 */
@Data
@ApiModel("设置窗口风格入参")
public class TvPlayStyleRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @NotEmpty(message = "预案ID不能为空")
    @ApiModelProperty(value = "预案ID - 必填，预案的配置ID")
    private Integer schid;

    @NotEmpty(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 必填，预案所属的大屏ID")
    private Integer tvid;

    @NotEmpty(message = "窗口ID不能为空")
    @ApiModelProperty(value = "窗口ID - 必填")
    private Integer wndid;

    @NotEmpty(message = "画面风格不能为空")
    @ApiModelProperty(value = "画面风格 - 必填，1=一画面，4=四画面")
    private Integer style;

}
