package com.kedacom.core.pojo;

import com.alibaba.fastjson.JSONException;

/**
 * @author van.shu
 * @create 2021/5/13 14:02
 */
public interface Response {


    Integer acquireSsno();

    String acquireName();


    /**
     * 获取数据
     * @param clazz clazz 对象
     * @param <T> 泛型
     * @return 结果
     * @throws JSONException
     */
    <T> T acquireData(Class<T> clazz) throws JSONException;


}
