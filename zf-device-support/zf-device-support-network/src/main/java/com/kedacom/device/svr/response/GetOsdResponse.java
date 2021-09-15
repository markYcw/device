package com.kedacom.device.svr.response;

import com.kedacom.device.svr.SvrResponse;

import com.kedacom.device.svr.pojo.OsdInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 18:50
 * @description 获取画面叠加
 */
@Data
public class GetOsdResponse extends SvrResponse {

    @ApiModelProperty("画面叠加参数")
    private OsdInfo osdInfo;

}
