package com.kedacom.device;

import com.alibaba.fastjson.JSONException;

/**
 * @author van.shu
 * @create 2021/5/13 14:02
 */
public interface Response {


    /**
     * 解包
     * @return 结果
     */
    <T> T parsePacket(String json, Class<T> clazz) throws JSONException;


}
