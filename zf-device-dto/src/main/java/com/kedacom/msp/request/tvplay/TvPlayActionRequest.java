package com.kedacom.msp.request.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 10:03
 */
@Data
@ApiModel(description = "窗口操作入参")
public class TvPlayActionRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填", required = true)
    private String token;

    @NotNull(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 必填", required = true)
    private Integer tvid;

    @NotNull(message = "窗口ID不能为空")
    @ApiModelProperty(value = "窗口ID - 必填", required = true)
    private Integer wndid;

    @NotNull(message = "子窗口索引不能为空")
    @ApiModelProperty(value = "子窗口索引 - 必填, 索引从0开始，255时为全部", required = true)
    private Integer index;

    @NotNull(message = "操作码不能为空")
    @ApiModelProperty(value = "操作码 - 必填, 0=播放，1=暂停", required = true)
    private Integer code;

}
