package com.kedacom.avIntegration.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 16:02
 */
@Data
@ApiModel("查询预案布局入参")
public class SchemeQueryRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @ApiModelProperty(value = "预案ID - 选填，预案的配置ID")
    private Integer id;

    @NotEmpty(message = "大屏ID不能为空")
    @ApiModelProperty(value = "大屏ID - 必填，预案所属的大屏ID")
    private Integer tvid;

}
