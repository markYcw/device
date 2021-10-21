package com.kedacom.aiBox.response;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/15
 */
@Data
@ApiModel(description =  "分页查询AI-Box设备信息列表返回参数类")
public class SelectPageResponseDto implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String abName;

    @ApiModelProperty(value = "ip")
    private String abIp;

    @ApiModelProperty(value = "端口")
    private Integer abPort;

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
