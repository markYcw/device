package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 13:51
 */
@Data
public class QueryAllAudioMixRequestDTO {

    @NotBlank(message = "统一平台Id不能为空")
    @ApiModelProperty("统一平台Id，必填")
    private String unitId;

    @ApiModelProperty("使用32位UUID（无横线）")
    private String groupID;

}
