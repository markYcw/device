package com.kedacom.msp.request.subscribe;

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
 * @Date: 2021/5/8 10:16
 */
@Data
@ApiModel(description = "订阅入参")
public class SubscribeStartRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @ApiModelProperty(value = "订阅发布地址，例如http://dev.ctsp.kedacom.com/displayServer/process_callback")
    private String url;

    @NotNull
    @ApiModelProperty(value = "订阅个数")
    private Integer number;

    @NotEmpty
    @ApiModelProperty(value = "订阅信息")
    private List<SubData> sub;

}
