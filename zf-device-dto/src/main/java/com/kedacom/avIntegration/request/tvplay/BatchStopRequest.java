package com.kedacom.avIntegration.request.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:42
 */
@Data
@ApiModel("关闭窗口显示入参")
public class BatchStopRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @NotNull(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 必填")
    private Integer tvid;

    @NotNull(message = "窗口个数不能为空")
    @ApiModelProperty(value = "窗口个数 - 必填，待关闭显示的窗口个数")
    private Integer number;

    @ApiModelProperty(value = "批量窗口信息")
    private List<BatchWindInfo> wndinfo;

}
