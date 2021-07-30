package com.kedacom.msp.request.tvwall;

import com.kedacom.msp.info.TvWallWndInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @ApiModelProperty(value = "token令牌 - 必填", required = true)
    private String token;

    @NotNull(message = "大屏ID不能为空")
    @ApiModelProperty(value = "必填 - 大屏ID", required = true)
    private Integer id;

    @ApiModelProperty(value = "解码通道使用模式 - 0=外置、1=内置，不填或填0时为外置")
    private Integer dec_mode;

    @ApiModelProperty(value = "窗口信息")
    private List<TvWallWndInfo> wndinfo;

}
