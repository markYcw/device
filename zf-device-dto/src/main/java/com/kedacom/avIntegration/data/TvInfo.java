package com.kedacom.avIntegration.data;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 11:08
 */
@Data
@ApiModel("大屏信息")
public class TvInfo implements Serializable {

    @ApiModelProperty(value = "大屏ID - 大屏配置所属的大屏ID")
    private Integer id;

    @ApiModelProperty(value = "大屏名称 - 大屏配置的显示名称，最大64个英文字符")
    private String name;

    @ApiModelProperty(value = "大屏分辨率 - 大屏的宽，单位：像素")
    private Integer res_width;

    @ApiModelProperty(value = "大屏分辨率 - 大屏的高，单位：像素")
    private Integer res_height;

    @ApiModelProperty(value = "大屏初始风格行数 - 大屏的纵向布局，规则屏时有效")
    private Integer cell_row;

    @ApiModelProperty(value = "大屏初始风格列数 - 大屏的横向布局，规则屏时有效")
    private Integer cell_col;

    @ApiModelProperty(value = "大屏模式 - (0=拼接屏模式; 1=虚拟屏模式（解码器）; 2=混合屏模式（解码器+拼接器）; 4=自定义屏（调度侧配置）)")
    private Integer module;

    @ApiModelProperty(value = "是否规则等分布局 - 默认是")
    private boolean isequant;

    @ApiModelProperty(value = "是否可以编辑 - 默认是")
    private boolean isedit;

}
