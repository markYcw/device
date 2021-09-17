package com.kedacom.svr.stragegy.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 13:52
 * @description dvd信息
 */
@Data
public class DvdInfo {

    @ApiModelProperty("DVD编号")
    private Integer dvdId;

    @ApiModelProperty("仓门状态 0：无效状态 1：仓门关闭，无盘片状态 2：仓门打开 3：仓门关闭，准备就绪,就绪之后才能开始刻录 4:仓门关闭，未准备就绪")
    private Integer doorState;

    @ApiModelProperty("光盘状态 0：无效盘 1：空白盘，可以第一次刻录 2：未封口盘，可以追加刻录 3：封口盘，除DVD+RW类型和DVD-RW的受限覆盖模式类型可以追加刻录外，其他类型盘不可刻录 4:其他未知，不可刻录")
    private Integer discState;

    @ApiModelProperty("光盘类型 0：未知 1：DVD-ROM 2：DVD-R Sequential 3：DVD-R Dual Layer Sequential 4:DVD-R Dual Layer Jump 5:DVD-RW Sequential 6:DVD-RW Restricted Overwrite 7:DVD-RAM 8:DVD+R 9:DVD+R Double Layer 10:DVD+RW " +
            "11:DVD+RW Double Layer 12:BD-ROM 13:BD-R SRM 14:BD-R SRM+POW 15:BD-R RRM 16:BD-RE 17:DVD-R Sequential Mini(总容量1.4G)")
    private Integer discType;

    @ApiModelProperty("光盘总容量，单位MB")
    private Integer totalCap;

    @ApiModelProperty("光盘剩余容量，单位MB")
    private Integer freeCap;

    @ApiModelProperty("刻录主状态 0：无效状态 1：空闲 2：光盘刻录 3：光盘读取")
    private Integer workState;

    @ApiModelProperty("刻录子状态 0：无效状态 1：空闲子状态：正常 2：刻录子状态：正常刻录 3：刻录子状态：刻录出错，==>换盘，重新刻录 4：刻录子状态：刻录过程中换盘 ==>继续刻录" +
            "5：刻录子状态：刻录换盘后无法刻录 ==>继续换盘 6：刻录子状态：停止刻录，不一定存在。 7：刻录子状态：封盘状态,庭审支持单独封盘操作 8：刻录子状态：刻录暂停状态,庭审支持刻录暂停" +
            "9：刻录子状态: 追加刻录,庭审支持追加刻录笔录文件 10：光盘读取子状态：正常 11：光盘读取子状态：读取出错")
    private Integer workSubState;

    @ApiModelProperty("剩余时间，单位秒")
    private Integer remainTime;

    @ApiModelProperty("刻录进度，百分比")
    private Integer progress;

}
