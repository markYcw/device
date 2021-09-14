package com.kedacom.device.svr.response;

import com.kedacom.device.svr.SvrResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 10:35
 * @description 获取编码器的预置位
 */
@Data
public class CpResetResponse extends SvrResponse {

    @ApiModelProperty("预置位1-255")
    private Integer preset;

}
