package com.kedacom.mt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/1
 */
@Data
public class MtLoginResponse extends MtResponse implements Serializable {

    @ApiModelProperty(value = "登录成功后会话id")
    private Integer ssid;

}
