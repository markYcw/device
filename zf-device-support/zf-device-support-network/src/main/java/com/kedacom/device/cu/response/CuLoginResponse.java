package com.kedacom.device.cu.response;

import com.kedacom.device.cu.CuResponse;
import com.kedacom.device.svr.SvrResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Author ycw
 * @Date: 2021/09/06 15:17
 * @Description 登录SVR中间件返回ssid
 */
@Data
@ToString(callSuper = true)
@ApiModel(description =  "登录SVR中间件返回")
public class CuLoginResponse extends CuResponse {

    @ApiModelProperty(value = "登录成功后会话id")
    private Integer ssid;

}
