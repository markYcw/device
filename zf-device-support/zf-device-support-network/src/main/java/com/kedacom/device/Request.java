package com.kedacom.device;

import com.alibaba.fastjson.JSONException;

/**
 * @author van.shu
 * @create 2021/5/13 13:57
 */
public interface Request {


    /**
     * 构建发送包
     * @return 构建
     */
    String buildPacket(Integer ssid) throws JSONException;

}
