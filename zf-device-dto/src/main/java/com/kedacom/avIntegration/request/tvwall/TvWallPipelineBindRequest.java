package com.kedacom.avIntegration.request.tvwall;

import com.kedacom.avIntegration.data.TvWallWndInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 14:50
 */
@Data
@ApiModel("配置大屏通道绑定入参")
public class TvWallPipelineBindRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @NotEmpty(message = "大屏ID不能为空")
    @ApiModelProperty(value = "必填 - 大屏ID")
    private Integer id;

    @ApiModelProperty(value = "解码通道使用模式 - 0=外置、1=内置，不填或填0时为外置")
    private Integer dec_mode;

    @ApiModelProperty(value = "窗口信息")
    private List<TvWallWndInfo> wndinfo;

}
