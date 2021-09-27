package com.kedacom.device.mp.mcu.response;

import com.kedacom.device.mp.MpResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/27 17:18
 * @description
 */
@Data
@ApiModel(description =  "创建账户返回结果")
public class AccountResponse extends MpResponse {

    @ApiModelProperty(value = "账号moId 创建成功时返回")
    private String accountMoId;

}
