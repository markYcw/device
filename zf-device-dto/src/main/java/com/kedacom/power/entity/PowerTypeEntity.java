package com.kedacom.power.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 电源设备类型表
 * </p>
 *
 * @author zlf
 * @since 2021-06-09
 */
@Data
@TableName("km_power_type")
@ApiModel(value="PowerTypeEntity对象", description="电源设备类型表")
public class PowerTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "电源设备类型数据库Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "电源设备类型")
    private String devType;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NOT_NULL)
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
