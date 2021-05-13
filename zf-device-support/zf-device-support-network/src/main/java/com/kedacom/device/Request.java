package com.kedacom.device;

import com.alibaba.fastjson.JSONException;

/**
 * @author van.shu
 * @create 2021/5/13 13:57
 */
public interface Request {


    /**
     * 构建发送数据
     * @param ssid ssid
     * @return 数据
     * @throws JSONException
     */
    String buildData(Integer ssid) throws JSONException;


    Integer getSsno();


    String getCommand();

}
