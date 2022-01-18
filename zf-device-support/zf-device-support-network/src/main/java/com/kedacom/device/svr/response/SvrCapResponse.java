package com.kedacom.device.svr.response;

import com.kedacom.device.svr.SvrResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SVR能力集响应体
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 10:32
 * @description
 */
@Data
public class SvrCapResponse extends SvrResponse {

    @ApiModelProperty("支持最大编码通道数")
    private Integer maxEncNum;

    @ApiModelProperty("支持最大解码通道数")
    private Integer maxDecNum;

    @ApiModelProperty("支持最大远程点通道数")
    private Integer maxRemChnNum;

    @ApiModelProperty("远程通道起始id")
    private Integer remChnIdStart;

}
