package com.kedacom.device.core.constant;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/17
 */
public interface Event {

    Integer OPERATETYPE = 10;

    //状态更新
    Integer OPERATETYPETYPE1 = 1;

    //GPS
    Integer OPERATETYPETYPE2 = 2;

    //新增
    Integer OPERATETYPETYPE3 = 3;

    //修改
    Integer OPERATETYPETYPE4 = 4;

    //删除
    Integer OPERATETYPETYPE5 = 5;

    //自动审核通过
    Integer OPERATETYPETYPE6 = 6;

    //表示设备与分组关系发生变化
    Integer OPERATETYPETYPE7 = 7;

}
