package com.kedacom.newMedia.resopnse;
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
@ApiModel(description =  "登录新媒体中间件返回")
public class NewMediaLoginResponse extends NewMediaResponse {

    @ApiModelProperty(value = "登录成功后会话id")
    private Integer ssid;

}
