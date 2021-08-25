package com.kedacom.msp.request.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 10:01
 */
@Data
@ApiModel(description = "窗口排序入参")
public class TvPlayOrderRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填", required = true)
    private String token;

    @NotNull(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 必填", required = true)
    private Integer tvid;

    @NotNull(message = "窗口ID不能为空")
    @ApiModelProperty(value = "窗口ID - 必填", required = true)
    private Integer wndid;

    @NotNull(message = "窗口显示顺序不能为空")
    @ApiModelProperty(value = "窗口显示顺序 - 必填, 0=置顶，1=置底，2=上移一层，3=下移一层", required = true)
    private Integer order;

}
