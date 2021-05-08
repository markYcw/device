package com.kedacom.avIntegration.request.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:43
 */
@Data
@ApiModel("关闭窗口显示-批量窗口信息")
public class BatchWindInfo implements Serializable {

    @NotEmpty(message = "窗口ID不能为空")
    @ApiModelProperty(value = "窗口ID - 必填，待关闭显示的窗口ID")
    private Integer id;

    @NotEmpty(message = "窗口内小窗索引不能为空")
    @ApiModelProperty(value = "窗口内小窗索引 - 必填，从0开始，从上到下，从左到右排布，255=关闭窗口内所有的小窗")
    private Integer wnd_index;

}
