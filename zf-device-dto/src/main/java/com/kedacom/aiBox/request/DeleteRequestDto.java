package com.kedacom.aiBox.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/18
 */
@Data
@ApiModel(description = "删除AI-Box设备信息请求参数类")
public class DeleteRequestDto implements Serializable {

    @ApiModelProperty("设备id集合")
    @NotEmpty(message = "设备id集合不能为空")
    private List<String> ids;

}
