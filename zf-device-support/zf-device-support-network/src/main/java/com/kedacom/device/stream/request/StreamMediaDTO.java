package com.kedacom.device.stream.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.kedacom.core.pojo.ReqHead;
import com.kedacom.core.pojo.Request;
import com.kedacom.util.NumGen;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:18
 */
@ToString(callSuper = true)
@Data
public abstract class StreamMediaDTO implements Request {

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
