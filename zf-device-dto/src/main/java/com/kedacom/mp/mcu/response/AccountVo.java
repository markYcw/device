package com.kedacom.mp.mcu.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/27 17:21
 * @description
 */
@Data
@ApiModel(description = "创建/删除账号响应")
public class AccountVo {

    @ApiModelProperty(value = "账号moid 创建成功时返回")
    private String accountMoId;


}
