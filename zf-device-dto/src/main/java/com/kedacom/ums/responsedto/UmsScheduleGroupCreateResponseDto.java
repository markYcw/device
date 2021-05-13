package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "创建调度组响应参数类")
public class UmsScheduleGroupCreateResponseDto implements Serializable {

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "录像CR交换节点")
    private String CRStorageID;

    @ApiModelProperty(value = "成员设备id")
    private List<String> DeviceIDs;

}
