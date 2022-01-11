package com.kedacom.vs.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 录播服务器类型
 *
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-05-14 17:01:24
 */
@Data
@TableName("km_vrs_name")
public class VsTypeEntity {
    /**
     * VRS标识
     */
    @ApiModelProperty("终端标识")
    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id;

    /**
     * VRS名称
     */
    @ApiModelProperty("终端名称")
    private String name;

}
