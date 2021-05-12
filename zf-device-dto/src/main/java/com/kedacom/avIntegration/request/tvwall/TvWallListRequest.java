package com.kedacom.avIntegration.request.tvwall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 11:03
 */
@Data
@ApiModel("获取大屏列表入参")
public class TvWallListRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填", required = true)
    private String token;

    @ApiModelProperty(value = "大屏ID - 不填或填0查询全部")
    private Integer tvid;

    @NotNull(message = "大屏模式不能为空")
    @ApiModelProperty(value = "必填， 大屏模式 - （0=拼接屏模式（默认）；1=虚拟屏模式（解码器）；2=混合屏模式（解码器+拼接器）；4=自定义屏（调度侧配置）；255=全部大屏）", required = true)
    private Integer module;

}
