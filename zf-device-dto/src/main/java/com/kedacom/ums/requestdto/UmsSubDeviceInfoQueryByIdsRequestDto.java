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
@ApiModel(value = "根据设备数据库的id查询统一平台下挂载的子设备信息")
public class UmsSubDeviceInfoQueryByIdsRequestDto implements Serializable {

    @ApiModelProperty(value = "设备id集合")
    private List<String> ids;

}
