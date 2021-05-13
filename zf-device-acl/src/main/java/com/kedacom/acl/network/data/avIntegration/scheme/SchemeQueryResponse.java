package com.kedacom.acl.network.data.avIntegration.scheme;

import com.kedacom.avIntegration.info.LayoutInfo;
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
public class SchemeQueryResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

    @ApiModelProperty(value = "预案ID")
    private Integer id;

    @ApiModelProperty(value = "大屏ID")
    private Integer tvid;

    @ApiModelProperty(value = "窗口布局参数")
    private List<LayoutInfo> layout;

}
