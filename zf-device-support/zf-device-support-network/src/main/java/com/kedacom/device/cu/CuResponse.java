package com.kedacom.device.cu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ycw
 * @Date: 2021/09/06 13:58
 * @Description 统一返回结果
 */
@Data
@ApiModel(description =  "统一返回结果")
public class CuResponse implements Serializable {

    @ApiModelProperty(value = "流水号")
    private String ssno;

    @ApiModelProperty(value = "错误码,0表示成功")
    private Integer code;

}
