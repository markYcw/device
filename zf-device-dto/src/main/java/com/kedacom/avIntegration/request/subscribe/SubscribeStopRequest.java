package com.kedacom.avIntegration.request.subscribe;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 11:01
 */
@Data
@ApiModel("停止订阅入参")
public class SubscribeStopRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @NotEmpty
    @ApiModelProperty(value = "订阅个数")
    private Integer number;

    @NotEmpty
    @ApiModelProperty(value = "订阅信息")
    private List<SubData> sub;

}
