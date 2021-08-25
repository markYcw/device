package com.kedacom.acl.network.data.avIntegration.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:45
 */
@Data
@ApiModel(description = "关闭窗口显示应答")
public class BatchStopResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

}
