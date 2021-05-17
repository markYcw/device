package com.kedacom.core.pojo;

import com.alibaba.fastjson.JSONException;

/**
 * @author van.shu
 * @create 2021/5/13 20:26
 */
public interface Notify {

    /**
     * 流水号
     * @return
     */
    Integer acquireSsno();

    /**
     * 命令值，可以理解为通知类型
     * @return
     */
    String acquireCommand();

    /**
     * 获取数据
     * @param clazz clazz 对象
     * @param <T> 泛型
     * @return 结果
     * @throws JSONException
     */
    <T> T acquireData(Class<T> clazz) throws JSONException;

}
