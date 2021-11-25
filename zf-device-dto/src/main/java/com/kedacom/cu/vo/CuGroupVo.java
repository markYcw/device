package com.kedacom.cu.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 监控平台分组信息
 * @author ycw
 * @create 2020/11/02 10:55
 */
@Data
public class CuGroupVo {

    /**
     * 组id
     */
    @ApiModelProperty("组id")
    private String id;

    /**
     * 分组名称
     */
    @ApiModelProperty("分组名称")
    private String name;

    /**
     * 上级组id
     */
    @ApiModelProperty("上级组id")
    private String parentId;

    /**
     * 子设备组
     */
    @ApiModelProperty("子设备组")
    private List<CuGroupVo> sortChildGroups = new LinkedList<CuGroupVo>();


    /**
     * 改分组下面是否有设备，平台2.0有效，方便刷设备。
     */
    @ApiModelProperty("改分组下面是否有设备，平台2.0有效，方便刷设备。")
    private Integer hasDev;


    /**
     * “未分组”的ID
     */
    @ApiModelProperty("“未分组”的ID")
    public static String unNamgedGroupId = "IsUnnamedGroup";

    /**
     * 设备列表
     */
    @ApiModelProperty("设备列表")
    private List<CuDeviceVo> childList;

    /**
     * 总设备数
     */
    @ApiModelProperty("总设备数")
    private Integer count;

    /**
     * 设备在线数
     */
    @ApiModelProperty("设备在线数")
    private Integer onLineCount;

    /**
     * uuid
     */
    @ApiModelProperty("uuid")
    private String uuid;

}

