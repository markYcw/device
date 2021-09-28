package com.kedacom.device.mp.mcu.request;

import com.kedacom.mp.mcu.pojo.AccountInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/13 13:38
 * @description 会议平台创建/删除账号向中间件请求参数
 */
@Data
@ApiModel(description =  "会议平台创建/删除账号向中间件请求参数")
public class McuAccountRequest implements Serializable {


    @NotNull(message = "操作账号类型不能为空")
    @ApiModelProperty(value = "0:创建账号;1:删除帐号", required = true)
    private Integer type;

    @ApiModelProperty(value = "删除时必填")
    private String accountMoId;

    @ApiModelProperty(value = "账户信息，创建时必填")
    private AccountInfo accountInfo;

}
