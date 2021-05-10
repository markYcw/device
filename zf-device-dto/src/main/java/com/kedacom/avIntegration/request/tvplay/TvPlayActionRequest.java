package com.kedacom.avIntegration.request.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 10:03
 */
@Data
@ApiModel("窗口操作入参")
public class TvPlayActionRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @NotEmpty(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 必填")
    private Integer tvid;

    @NotEmpty(message = "窗口ID不能为空")
    @ApiModelProperty(value = "窗口ID - 必填")
    private Integer wndid;

    @NotEmpty(message = "子窗口索引不能为空")
    @ApiModelProperty(value = "子窗口索引 - 必填, 索引从0开始，255时为全部")
    private Integer index;

    @NotEmpty(message = "操作码不能为空")
    @ApiModelProperty(value = "操作码 - 必填, 0=播放，1=暂停")
    private Integer code;

}