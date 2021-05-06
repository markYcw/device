package com.kedacom.avIntegration.response;

import com.kedacom.avIntegration.data.LayoutData;
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
@ApiModel("设置预案布局应答")
public class SchemeConfigResponse implements Serializable {

    @ApiModelProperty(value = "预案ID - 预案的配置ID，用于预案的切换和预案内画面的操作资源参数")
    private Integer id;

    @ApiModelProperty(value = "窗口数组")
    private List<LayoutData> layout;

}
