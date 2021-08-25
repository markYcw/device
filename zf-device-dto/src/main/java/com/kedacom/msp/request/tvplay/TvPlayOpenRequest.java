package com.kedacom.msp.request.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 09:56
 */
@Data
@ApiModel(description = "任意开窗入参")
public class TvPlayOpenRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填", required = true)
    private String token;

    @NotNull(message = "预案ID不能为空")
    @ApiModelProperty(value = "预案ID - 必填，预案的配置ID", required = true)
    private Integer schid;

    @NotNull(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 必填，预案所属的大屏ID", required = true)
    private Integer tvid;

    @ApiModelProperty(value = "窗口信息")
    private List<TvPlayOpenLayoutData> wnds;

}
