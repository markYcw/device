package com.kedacom.mt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/1
 */
@Data
@ApiModel(description =  "终端响应统一返回结果")
public class MtResponse implements Serializable {

    @ApiModelProperty(value = "流水号")
    private String ssno;

    @ApiModelProperty(value = "错误码,0表示成功")
    private Integer code;

}
