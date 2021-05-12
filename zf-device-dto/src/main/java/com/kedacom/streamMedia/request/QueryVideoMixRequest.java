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
 * @Date: 2021/4/30 15:03
 */
@Data
@ApiModel("查询画面合成信息入参")
public class QueryVideoMixRequest implements Serializable {

    @NotBlank(message = "统一平台Id不能为空")
    @ApiModelProperty("统一平台Id，必填")
    private String unitId;

    @NotEmpty(message = "合成ID集合不能为空")
    @ApiModelProperty("合成ID集合")
    private List<String> mixIDs;

}
