package com.kedacom.core.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.kedacom.util.NumGen;
import lombok.Data;
import lombok.ToString;

/**
 * @author van.shu
 * @date 2021/5/17 0:17
 */
@Data
@ToString(callSuper = true)
public abstract class BaseRequest implements Request {

    private ReqHead req;

    private void setReq(ReqHead req) {
        this.req = req;
    }

    @Override
    public String packet() throws JSONException {
        buildHead();
        return JSON.toJSONString(this);
    }

    private void buildHead() {
        if (this.req == null) {
            this.req = new ReqHead();
        }
        this.req.setSsno(NumGen.getNum());
        this.req.setName(name());
    };

    public abstract String name();

    public void setSsid(Integer ssid) {
        if (this.req == null) {
            this.req = new ReqHead();
        }
        if (ssid == null || ssid <= 0) {
            throw new IllegalArgumentException("ssid is invalid ; ssid = " + ssid);
        }
        this.req.setSsid(ssid);
    }


    public Integer acquireSsid() {
        return req.getSsid();
    }

    @Override
    public Integer acquireSsno() {
        return req.getSsno();
    }

    @Override
    public String acquireName() {
        return req.getName();
    }

}
