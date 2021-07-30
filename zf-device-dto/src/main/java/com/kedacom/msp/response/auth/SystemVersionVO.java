package com.kedacom.msp.response.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 10:13
 */
@Data
@ApiModel("鉴权获取版本返回")
public class SystemVersionVO implements Serializable {

    @ApiModelProperty("版本")
    private String version;

}
