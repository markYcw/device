package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date 2022/6/22 10:34
 */
@Data
@ApiModel(description = "事件订阅入参")
public class MeetRecEventSubDTO implements Serializable {

    @NotBlank(message = "平台id不能为空")
    @ApiModelProperty(value = "平台id", required = true)
    private String umsId;

    @NotBlank(message = "订阅ID号不能为空")
    @ApiModelProperty(value = "订阅ID号（用户自定义，新的订阅id就是创建，已使用的订阅id且持续时间不为0就是更新，为0就是取消）", required = true)
    private String subscribeId;

    @ApiModelProperty(value = "事件类别1: 容量相关异常 2: 录像相关异常 默认所有类型")
    private List<Integer> eventType;

    @NotBlank(message = "接收事件通知的url地址不能为空")
    @ApiModelProperty(value = "接收事件通知的url地址（用户自定义，事件订阅时为必填字段）", required = true)
    private String notifyUrl;

    @NotNull(message = "订阅持续时间不能为空")
    @ApiModelProperty(value = "订阅持续时间，取值范围为(0-3600)单位秒。expires为0时表示取消订阅", required = true)
    private Integer expires;

}
