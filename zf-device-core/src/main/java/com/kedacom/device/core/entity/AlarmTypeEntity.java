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
@TableName("ums_new_alarm_type")
public class AlarmTypeEntity implements Serializable {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "报警类型标识")
    @TableField(value = "alarm_code")
    private String alarmCode;

    @ApiModelProperty(value = "报警类型描述")
    @TableField(value = "alarm_desc")
    private String alarmDesc;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
