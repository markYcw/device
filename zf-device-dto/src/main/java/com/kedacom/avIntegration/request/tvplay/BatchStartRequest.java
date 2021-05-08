package com.kedacom.avIntegration.request.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:33
 */
@Data
@ApiModel("窗口显示入参")
public class BatchStartRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @NotEmpty(message = "预案ID不能为空")
    @ApiModelProperty(value = "预案ID - 必填，当前使用的预案ID")
    private Integer schid;

    @NotEmpty(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 必填，预案所在的大屏ID")
    private Integer tvid;

    @NotEmpty(message = "窗口个数不能为空")
    @ApiModelProperty(value = "窗口个数 - 必填，需要显示信号的窗口个数")
    private Integer number;

    @ApiModelProperty(value = "批量窗口信息")
    private List<BatchWndInfo> wndinfo;

}
