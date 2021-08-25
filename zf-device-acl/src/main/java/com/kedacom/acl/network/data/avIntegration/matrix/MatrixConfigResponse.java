package com.kedacom.acl.network.data.avIntegration.matrix;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 18:04
 */
@Data
@ApiModel(description = "设置矩阵切换应答")
public class MatrixConfigResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

}
