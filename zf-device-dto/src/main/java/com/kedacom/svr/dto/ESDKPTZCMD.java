package com.kedacom.svr.dto;

/**
 * @author ycw
 * SVR ptz控制命令枚举类
 * @date 2021/09/10
 */
enum PtzCmd {

    SVRSDK_PTZ_COMMAND_NULL              (0),               //无效命令
    SVRSDK_PTZ_COMMAND_MOVEUP            (1),             //向上移动  param1 0~15,param2 0~15
    SVRSDK_PTZ_COMMAND_MOVEDOWN          (2),               //向下移动  同上
    SVRSDK_PTZ_COMMAND_MOVELEFT          (3),                //向左移动  同上
    SVRSDK_PTZ_COMMAND_MOVERIGHT         (4),                //向右移动  同上
    SVRSDK_PTZ_COMMAND_MOVEHOME          (5),                //回归      同上
    SVRSDK_PTZ_COMMAND_MOVESTOP          (6),                //停止移动  同上
    SVRSDK_PTZ_COMMAND_ZOOMTELE          (7),                //拉近摄像头
    SVRSDK_PTZ_COMMAND_ZOOMWIDE          (8),                //拉远摄像头    同上
    SVRSDK_PTZ_COMMAND_ZOOMSTOP          (9),                //视野调节停止  无意义
    SVRSDK_PTZ_COMMAND_FOCUSFAR          (10),                //将焦距调远    无意义
    SVRSDK_PTZ_COMMAND_FOCUSNEAR         (11),                //将焦距调近    无意义
    SVRSDK_PTZ_COMMAND_FOCUSAUTO         (12),                //自动调焦      无意义
    SVRSDK_PTZ_COMMAND_FOCUSSTOP         (13),                //调焦停止      无意义
    SVRSDK_PTZ_COMMAND_PRESETSET         (14),                //摄象头预存    param1：0~255
    SVRSDK_PTZ_COMMAND_PRESETCALL        (15),                //调摄象头预存  同上
    SVRSDK_PTZ_COMMAND_CAMERASET         (16),                //初始化摄像头  无
    SVRSDK_PTZ_COMMAND_BRIGHTUP          (17),                //画面调亮      无
    SVRSDK_PTZ_COMMAND_BRIGHTDOWN        (18),                //画面调暗      无
    SVRSDK_PTZ_COMMAND_BRIGHTSTOP        (19),                //停止调亮      无
    SVRSDK_PTZ_COMMAND_GOTOPOINT         (20),                //中心定位      x坐标:param1, Y坐标:param2
    SVRSDK_PTZ_COMMAND_ZOOMPART          (21),                //局部放大      x坐标:param1, Y坐标:param2, 宽度:param3,高度:param4
    SVRSDK_PTZ_COMMAND_STARTAUTOSCAN     (22),            //开始自动巡航
    SVRSDK_PTZ_COMMAND_STOPAUTOSCAN      (23),          //停止自动巡航
    SVRSDK_PTZ_COMMAND_STARTTOUR         (24),            //巡视开始
    SVRSDK_PTZ_COMMAND_ENDTOUR           (25),            //巡视结束
    SVRSDK_PTZ_COMMAND_ZOOMWHOLE         (26),            //局部缩小        x坐标:param1, Y坐标:param2, 宽度:param3,高度:param4
    SVRSDK_PTZ_COMMAND_APERTUREMODE_AUTO (27),      //光圈自动
    SVRSDK_PTZ_COMMAND_HORIZONTAL_TURN   (28),            //云台水平180度翻转
    SVRSDK_PTZ_COMMAND_MOVE_LEFTUP       (29),             //向左上移动
    SVRSDK_PTZ_COMMAND_MOVE_LEFTDOWN     (30),            //向左下移动
    SVRSDK_PTZ_COMMAND_MOVE_RIGHTUP      (31),            //向右上移动
    SVRSDK_PTZ_COMMAND_MOVE_RIGHTDOWN    (32);           //向右下移动

    private int type;

    PtzCmd(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
