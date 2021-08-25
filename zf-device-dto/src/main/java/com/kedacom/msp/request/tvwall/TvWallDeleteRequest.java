package com.kedacom.msp.request.tvwall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 15:09
 */
@Data
@ApiModel(description = "删除大屏入参")
public class TvWallDeleteRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填", required = true)
    private String token;

    @NotNull(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 按需填写，新建大屏填0或不填，修改大屏必填", required = true)
    private Integer id;

}
