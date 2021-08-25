package com.kedacom.msp.request.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:43
 */
@Data
@ApiModel(description = "关闭窗口显示-批量窗口信息")
public class BatchWindInfo implements Serializable {

    @NotNull(message = "窗口ID不能为空")
    @ApiModelProperty(value = "窗口ID - 必填，待关闭显示的窗口ID", required = true)
    private Integer id;

    @NotNull(message = "窗口内小窗索引不能为空")
    @ApiModelProperty(value = "窗口内小窗索引 - 必填，从0开始，从上到下，从左到右排布，255=关闭窗口内所有的小窗", required = true)
    private Integer wnd_index;

}
