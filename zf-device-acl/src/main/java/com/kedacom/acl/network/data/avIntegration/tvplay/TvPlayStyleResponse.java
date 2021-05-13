package com.kedacom.acl.network.data.avIntegration.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 09:55
 */
@Data
@ApiModel("设置窗口风格应答")
public class TvPlayStyleResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

}
