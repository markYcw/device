package com.kedacom.mp.mcu.response;

import com.kedacom.mp.mcu.pojo.ListConfInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/12 19:24
 * @description 获取会议列表响应
 */
@Data
@ApiModel(value = "获取会议列表响应")
public class McuConfsVO implements Serializable {

    @ApiModelProperty(value = "会议列表")
    private List<ListConfInfo> confInfo;

}
