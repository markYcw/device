package com.kedacom.avIntegration.request.decoder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 17:56
 */
@Data
@ApiModel(value = "设置解码通道风格信息入参")
public class StyleConfigRequest implements Serializable {

    @NotEmpty(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @ApiModelProperty(value = "解码通道风格信息")
    private List<StyleConfigChnInfo> chnls;

}
