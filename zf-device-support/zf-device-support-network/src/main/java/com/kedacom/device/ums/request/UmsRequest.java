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

    abstract String getCommand();

    @Override
    public String buildData(Integer ssid) throws JSONException {
        buildHead(ssid);
        return JSON.toJSONString(this);
    }

    public int getSsno() {
        return req.getSsno();
    }

    public int getSsid() {
        return req.getSsid();
    }


}
