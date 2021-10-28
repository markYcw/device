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
 * @date 2021/10/15
 */
@Data
@TableName("ums_new_ai_box")
public class AiBoxEntity implements Serializable {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @NotEmpty(message = "设备名称不能为空")
    @ApiModelProperty(value = "设备名称")
    private String abName;

    @NotEmpty(message = "设备IP不能为空")
    @ApiModelProperty(value = "IP")
    private String abIp;

    @NotEmpty(message = "设备端口不能为空")
    @ApiModelProperty(value = "端口")
    private Integer abPort;

    @ApiModelProperty("设备用户名")
    @NotEmpty(message = "设备用户名不能为空")
    private String abUsername;

    @ApiModelProperty("设备登录密码")
    @NotEmpty(message = "设备登录密码不能为空")
    private String abPassword;

    @ApiModelProperty("能识别的人脸最小像素值(默认为60)")
    @NotEmpty(message = "最小像素值不能为空")
    private String abMinFace;

    @ApiModelProperty("能识别的人脸最大像素值(默认为400)")
    @NotEmpty(message = "最大像素值不能为空")
    private String abMaxFace;

    @ApiModelProperty(value = "设备名称拼音(拼音+首字母)")
    private String abPinyin;

    @ApiModelProperty(value = "描述")
    private String abDesc;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
