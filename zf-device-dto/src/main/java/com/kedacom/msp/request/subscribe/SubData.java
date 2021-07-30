package com.kedacom.msp.request.subscribe;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 11:02
 */
@Data
@ApiModel("订阅类型")
public class SubData implements Serializable {

    @ApiModelProperty(value = "订阅类型 0：全部、1：大屏列表变化通知、2：当前预案变化通知、4：收发盒信号变化通知")
    private Integer type;

}
