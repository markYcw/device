package com.kedacom.device.svr.response;

import com.kedacom.device.svr.SvrResponse;
import com.kedacom.device.svr.pojo.RemoteCfgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/10 15:55
 * @description 远程点配置
 */
@Data
public class RemoteCfgVoResponse extends SvrResponse {


    @ApiModelProperty("是否将远程点作为合成通道 0：不作为 1:作为")
    private RemoteCfgInfo remoteCfg;

}
