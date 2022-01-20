package com.kedacom.svr.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * SVR设备类型表
 * </p>
 *
 * @author ycw
 * @since 2021-01-06
 */
@Data
@TableName("km_svr_type")
@ApiModel(value="SvrTypeEntity对象", description="SVR设备类型表")
public class SvrTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "SVR设备类型数据库Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "SVR设备类型 如SVR2931")
    private String type;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

}
