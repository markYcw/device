package com.kedacom.data.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据迁移表
 *
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2022-04-28 17:01:24
 */
@Data
@TableName("ums_data")
public class DataEntity {

    @ApiModelProperty("数据迁移ID")
    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("数据迁移标识")
    private Integer dc;

}
