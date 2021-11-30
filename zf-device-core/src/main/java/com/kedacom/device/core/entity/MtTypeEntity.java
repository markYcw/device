package com.kedacom.device.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/29
 */
@Data
@TableName("ums_mt_type")
public class MtTypeEntity {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "终端设备类型")
    private Integer mtType;

    @ApiModelProperty(value = "终端设备名称")
    private String mtName;

    @ApiModelProperty(value = "终端设备版本，0:3.0; 1:5.0")
    private Integer mtVersion;

}
