package com.kedacom.core.pojo;

import com.alibaba.fastjson.JSONException;

/**
 * @author van.shu
 * @create 2021/5/13 13:57
 */
public interface Request {


    /**
     * 构建发送数据
     * @param ssid ssid
     * @throws JSONException
     */
    String packet() throws JSONException;


    Integer acquireSsno();


    String acquireName();

}
