package com.kedacom.device.svr.response;

import com.kedacom.device.svr.SvrResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取SVR时间响应体
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 11:04
 * @description
 */
@Data
public class SvrTimeResponse extends SvrResponse {

    @ApiModelProperty("时间")
    private String time;
}
