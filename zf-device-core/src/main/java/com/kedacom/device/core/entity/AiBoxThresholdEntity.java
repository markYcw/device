package com.kedacom.device.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/3
 */
@Data
@TableName("ums_new_ai_box_threshold")
public class AiBoxThresholdEntity implements Serializable {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @NotEmpty(message = "设备ID不能为空")
    @ApiModelProperty(value = "设备ID")
    private String abId;

    @ApiModelProperty("能识别的人脸最小像素值(默认为60)")
    @NotEmpty(message = "最小像素值不能为空")
    private Integer abMinFace;

    @ApiModelProperty("能识别的人脸最大像素值(默认为400)")
    @NotEmpty(message = "最大像素值不能为空")
    private Integer abMaxFace;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
