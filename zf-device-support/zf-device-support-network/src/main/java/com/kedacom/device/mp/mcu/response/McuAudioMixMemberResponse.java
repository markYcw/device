package com.kedacom.device.mp.mcu.response;

import com.kedacom.device.mp.MpResponse;
import com.kedacom.mp.mcu.pojo.MtInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/19 14:50
 * @description mcu添加/删除混音成员中间件响应
 */
@Data
@ApiModel(description =  "mcu添加/删除混音成员中间件响应")
public class McuAudioMixMemberResponse extends MpResponse {

    @ApiModelProperty(value = "终端")
    private List<MtInfo> mtInfos;

}
