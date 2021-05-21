package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/21
 */
@Data
public class UmsChildGroupAndSubDeviceInfoResponseVo implements Serializable {

    @ApiModelProperty(value = "当前分组下的子分组信息集合")
    private List<SelectChildUmsGroupResponseDto> childGroupList;

    @ApiModelProperty(value = "当前分组下的子设备信息集合")
    private List<UmsSubDeviceInfoQueryResponseDto> subDeviceList;

}
