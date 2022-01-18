package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 14:49
 * @description
 */
@Data
public class BurnStatesInfo {

    @ApiModelProperty("刻录任务id")
    private Integer burnTaskId;

    @ApiModelProperty("刻录通道id")
    private Integer burnChnId;

    @ApiModelProperty("刻录状态 0：空闲 1：刻录中")
    private Integer burnState;

    @ApiModelProperty("刻录模式 0：无效 1：双盘同步刻录 2：只刻录DVD1(暂不支持) 3：只刻录DVD2(暂不支持) 4：循环连续刻录")
    private Integer burnMode;

    @ApiModelProperty("工作模式： 0：实时刻录 1：补刻 2：只录像不刻录")
    private Integer workMode;

    @ApiModelProperty("dvd列表")
    private List<Dvd> dvds;




}
