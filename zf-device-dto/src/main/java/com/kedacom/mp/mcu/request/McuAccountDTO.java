package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.pojo.AccountInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author hxj
 * @Date: 2021/8/12 14:48
 * @Description 会议平台创建/删除账号入参
 */
@Data
@ApiModel(description =  "会议平台创建/删除账号入参")
public class McuAccountDTO extends McuRequestDTO {

    @NotNull(message = "操作账号类型不能为空")
    @ApiModelProperty(value = "0:创建账号;1:删除帐号", required = true)
    private Integer type;

    @ApiModelProperty(value = "删除时必填")
    private String accountMoId;

    @ApiModelProperty(value = "账户信息，创建时必填")
    @Valid
    private AccountInfo accountInfo;
}
