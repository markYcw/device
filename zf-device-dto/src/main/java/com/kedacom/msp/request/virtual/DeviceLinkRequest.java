package com.kedacom.msp.request.virtual;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 18:09
 */
@Data
@ApiModel("拼控连接控制入参")
public class DeviceLinkRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @NotNull
    @ApiModelProperty("大屏ID")
    private Integer tvid;

    @NotBlank
    @ApiModelProperty("连接控制码，on=重新重连、off=释放连接")
    private String code;

}
