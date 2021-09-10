package com.kedacom.device.svr.response;

import com.kedacom.device.svr.SvrResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 17:07
 * @description 获取解码参数
 */
@Data
public class DecParamResponse extends SvrResponse {

    @ApiModelProperty("解码通道ID")
    private Integer chnId;

    @ApiModelProperty("解码的源通道ID")
    private Integer srcChnId;

    @ApiModelProperty("是否解码辅流")
    private Integer isSecChn;

}
