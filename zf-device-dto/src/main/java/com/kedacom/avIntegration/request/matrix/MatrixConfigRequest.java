package com.kedacom.avIntegration.request.matrix;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 17:59
 */
@Data
@ApiModel("设置矩阵切换入参")
public class MatrixConfigRequest implements Serializable {

    @NotEmpty(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @NotNull
    @ApiModelProperty(value = "发生交换的通道个数 必填")
    private Integer number;

    @NotNull
    @ApiModelProperty("交换通道信息 必填")
    private List<Exchange> exchange;

    @Data
    class Exchange{

        @NotBlank
        @ApiModelProperty("信号源ID 必填，音视频信号的国标编号")
        private String srcid;

        @NotBlank
        @ApiModelProperty("目标通道ID 必填，音视频输出的国标编号")
        private String dstid;

        @NotNull
        @ApiModelProperty("交换类型 必填，0=视频、1=音频")
        private Integer type;

        @NotNull
        @ApiModelProperty("交换开关 必填，0=关、1=开")
        private Integer opt;
    }
}
