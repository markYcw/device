package com.kedacom.acl.network.data.avIntegration.scheme;

import com.kedacom.msp.info.LayoutData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 15:54
 */
@Data
@ApiModel(description = "设置预案布局应答")
public class SchemeConfigResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

    @ApiModelProperty(value = "预案ID - 预案的配置ID，用于预案的切换和预案内画面的操作资源参数")
    private Integer id;

    @ApiModelProperty(value = "窗口数组")
    private List<LayoutData> layout;

}
