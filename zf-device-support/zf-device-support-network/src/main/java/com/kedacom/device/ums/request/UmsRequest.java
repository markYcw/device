package com.kedacom.device.ums.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.kedacom.device.ReqHead;
import com.kedacom.device.Request;
import com.kedacom.util.NumGen;

/**
 * @author van.shu
 * @create 2021/5/13 13:57
 */
public abstract class UmsRequest implements Request {

    ReqHead req;

    public void buildHead(Integer ssid) {
        ReqHead req = new ReqHead();
        req.setSsno(NumGen.getNum());
        req.setName(getCommand());
        req.setSsid(ssid);
        this.req = req;
    }

    @Override
    public String getCommand() {
        //TODO 子类需要实现
        return null;
    }

    @Override
    public String buildData(Integer ssid) throws JSONException {
        buildHead(ssid);
        return JSON.toJSONString(this);
    }


    @Override
    public Integer getSsno() {
        return req.getSsno();
    }


    public int getSsid() {
        return req.getSsid();
    }


}
