package com.kedacom.avIntegration.vo.scheme;

import com.kedacom.avIntegration.data.LayoutInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 15:57
 */
@Data
@ApiModel("查询预案布局应答")
public class SchemeQueryVO implements Serializable {

    @ApiModelProperty(value = "预案ID")
    private Integer id;

    @ApiModelProperty(value = "大屏ID")
    private Integer tvid;

    @ApiModelProperty(value = "窗口布局参数")
    private List<LayoutInfo> layout;

}
