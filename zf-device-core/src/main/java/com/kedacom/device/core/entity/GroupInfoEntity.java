package com.kedacom.device.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
@Data
@TableName("ums_new_group")
public class GroupInfoEntity implements Serializable {

    @ApiModelProperty(value = "数据库Id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "分组Id")
    @TableField(value = "group_id")
    private String groupId;

    @ApiModelProperty(value = "分组父Id")
    @TableField(value = "parent_id")
    private String parentId;

    @ApiModelProperty(value = "分组名称")
    @TableField(value = "group_name")
    private String groupName;

    @ApiModelProperty(value = "分组所在设备Id")
    @TableField(value = "group_devid")
    private String groupDevId;

    @ApiModelProperty(value = "分组排序字段")
    @TableField(value = "sort_index")
    private Integer sortIndex;

}
