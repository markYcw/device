package com.kedacom.mp.mcu.response;

import com.kedacom.mp.mcu.pojo.MtInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 10:44
 * @description 获取与会成员响应
 */
@Data
@ApiModel(value = "获取与会成员响应")
public class McuMtmembersVO implements Serializable {

    @ApiModelProperty(value = "终端列表，终端id")
    private List<MtInfo> mtInfos;

}
