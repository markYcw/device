package com.kedacom.device.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/11
 */
@Data
@TableName("ums_new_device_type")
public class DeviceTypeEntity implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(value = "device_code")
    @ApiModelProperty(value = "类型编码")
    private String deviceCode;

    @TableField(value = "device_value")
    @ApiModelProperty(value = "设备类型")
    private String deviceValue;

    @TableField(value = "device_type_desc")
    @ApiModelProperty(value = "设备类型描述")
    private String typeDesc;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
