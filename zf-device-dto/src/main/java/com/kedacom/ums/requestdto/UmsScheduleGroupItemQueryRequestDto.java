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
@Data@ApiModel(description =  "根据子设备条件查询统一平台下分组中设备状况信息请求参数类")
public class UmsScheduleGroupItemQueryRequestDto implements Serializable {

    @ApiModelProperty("统一平台id")
    private String umsId;

    @ApiModelProperty("按统一平台子设备名称模糊搜索")
    private String deviceName;

    @ApiModelProperty("按统一设备子设备的类型搜索，支持多个设备类型，OR条件")
    private List<String> deviceTypeList;

    @ApiModelProperty("按统一设备子设备的状态搜索 0:在线 1:离线 2:故障")
    private Integer deviceStatus;

}
