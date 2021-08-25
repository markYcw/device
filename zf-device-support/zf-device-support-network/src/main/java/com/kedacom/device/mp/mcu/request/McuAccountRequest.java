package com.kedacom.device.mp.mcu.request;

import com.kedacom.mp.mcu.pojo.AccountInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/13 13:38
 * @description 会议平台创建/删除账号向中间件请求参数
 */
@Data
@ApiModel(description =  "会议平台创建/删除账号向中间件请求参数")
public class McuAccountRequest implements Serializable {

    @ApiModelProperty(value = "0:创建账号;1:删除帐号", required = true)
    private Integer type;

    @ApiModelProperty(value = "账号自定义账号，登陆账号\n" +
            "1.字符限制：\n" +
            "  a.不支持输入特殊字符：% & * ^ ~ ' \" \" ? / \\ <> | ` \" $\n" +
            "  b.且首字符和尾字符不支持输入，下划线（_） 减号（-） 小数点（.） @\n" +
            "（除首尾字符可以输入）\n" +
            "2.最大字符长度：64个字节", required = true)
    private String account;

    @ApiModelProperty(value = "账户信息，创建时必填")
    private AccountInfo accountInfo;

}
