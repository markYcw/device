package com.kedacom.newMedia.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/5/27 13:27
 * @description
 */
@Data
public class BurnInfo {

    @ApiModelProperty(value = "消息类型 0:未知 1:刻录状态 2:刻录进度 3:刻录错误 4:刻录全部完成 5:追加刻录电子笔录完成 6:刻录全状态,此时所有成员均有效")
    private Integer type;

    @ApiModelProperty(value = "刻录通道id")
    private Integer burnChnId;

    @ApiModelProperty(value = "状态，当MsgType为1时有效 0:空闲状态， 1:正在刻录 2:刻录完成 3:正在停止刻录中 4:正在补刻/重新刻录状态中 5:正在追加刻录 6:正在刻录平台录像")
    private Integer burnState;

    @ApiModelProperty(value = "光盘总大小单位mb")
    private Integer totalSpace;

    @ApiModelProperty(value = "光盘剩余大小单位mb")
    private Integer freeSpace;

    @ApiModelProperty(value = "错误码")
    private Integer errorCode;

    @ApiModelProperty(value = "dvd编号")
    private Integer dvdId;


}
