package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 14:27
 */
@Data
@ApiModel("停止画面合成入参")
public class StopVideoMixRequest implements Serializable {

    @NotBlank(message = "统一平台Id不能为空")
    @ApiModelProperty("统一平台Id，必填")
    private String unitId;

    @NotBlank(message = "使用32位UUID不能为空")
    @ApiModelProperty("使用32位UUID（无横线）")
    private String GroupID;

    @NotEmpty(message = "画面合成ID不能为空")
    @ApiModelProperty("画面合成ID集合")
    private List<String> mixIDs;

}
