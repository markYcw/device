package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 根据条件查询监控平台设备
 * @author ycw
 * @create 2021/08/20 13:37
 */
@Data
public class FindCuByConditionVo {

    @ApiModelProperty(value = "监控平台数据库ID",required = true)
    private Integer kmId;

    @ApiModelProperty("设备名称")
    private String name;

    @ApiModelProperty(value = "设备状态 0:全部设备 1：所有在线设备 2：离线设备",required = true)
    private Integer status;

    @ApiModelProperty("设备类型1: 编码器 2：解码器 4电视墙（2.0） 5 NVR（2.0） 6：SVR 7: 告警主机")
    private Integer deviceType;

}
