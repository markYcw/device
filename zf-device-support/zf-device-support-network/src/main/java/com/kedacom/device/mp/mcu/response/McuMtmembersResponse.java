package com.kedacom.device.mp.mcu.response;

import com.kedacom.device.mp.MpResponse;
import com.kedacom.mp.mcu.pojo.MtInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 10:44
 * @description 获取与会成员中间件响应
 */
@Data
@ApiModel(value = "获取与会成员中间件响应")
public class McuMtmembersResponse extends MpResponse {

    @ApiModelProperty(value = "终端列表，终端id")
    private List<MtInfo> mtInfos;

}
