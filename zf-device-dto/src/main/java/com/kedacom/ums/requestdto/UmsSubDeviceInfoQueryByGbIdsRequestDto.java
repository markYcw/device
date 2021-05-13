package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/11
 */
@Data
@ApiModel(value = "根据国标id查询子设备信息请求参数类")
public class UmsSubDeviceInfoQueryByGbIdsRequestDto implements Serializable {

    @ApiModelProperty(value = "国标id")
    private List<String> gbIds;

}
