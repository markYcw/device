package com.kedacom.mp.mcu.response;

import com.kedacom.mp.mcu.pojo.AccountInfoMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/27 17:21
 * @description
 */
@Data
@ApiModel(description = "查询所有账户响应")
public class AccountsVo {

    @ApiModelProperty(value = "会议模板ID，创建时返回")
    private int total;

    @ApiModelProperty(value = "会议模板ID，创建时返回")
    private List<AccountInfoMessage> accountInfo;


}
