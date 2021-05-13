package com.kedacom.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
    private String groupId;

    @ApiModelProperty(value = "分组父Id")
    private String parentId;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "umsId")
    private String umsId;

    @ApiModelProperty(value = "分组排序字段")
    private Integer sortIndex;

}
