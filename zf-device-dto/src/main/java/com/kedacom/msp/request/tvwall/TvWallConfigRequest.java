package com.kedacom.msp.request.tvwall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 15:06
 */
@Data
@ApiModel(description = "设置大屏入参")
public class TvWallConfigRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填", required = true)
    private String token;

    @NotNull(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 按需填写，新建大屏填0或不填，修改大屏必填", required = true)
    private Integer id;

    @ApiModelProperty(value = "大屏名称 - 按需填写，新建时必填，修改时选填。最大字符数32个")
    private String name;

    @ApiModelProperty(value = "大屏分辨率 - 大屏的宽，单位：像素")
    private Integer res_width;

    @ApiModelProperty(value = "大屏分辨率 - 大屏的高，单位：像素")
    private Integer res_height;

    @ApiModelProperty(value = "大屏初始风格行数 - 大屏的纵向布局，融合调度1.6版本以上该字段不用")
    private Integer cell_row;

    @ApiModelProperty(value = "大屏初始风格列数 - 大屏的横向布局，融合调度1.6版本以上该字段不用")
    private Integer cell_col;

    @NotNull(message = "大屏模式不能为空")
    @ApiModelProperty(value = "必填, 大屏模式 - 1=虚拟屏模式（解码器）;4=自定义屏（调度侧配置）", required = true)
    private Integer module;

}
