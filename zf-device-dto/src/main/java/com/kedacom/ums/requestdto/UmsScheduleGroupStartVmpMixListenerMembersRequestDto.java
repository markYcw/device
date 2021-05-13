package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "开始画面合成观看画面合成的成员设备id列表请求参数类")
public class UmsScheduleGroupStartVmpMixListenerMembersRequestDto implements Serializable {

    @ApiModelProperty(value = "观看画面合成的成员设备id列表，当不填broadcast字段 时listeners才有效")
    private String deviceId;

}
