package com.kedacom.device.ums.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.kedacom.device.RespHead;
import com.kedacom.device.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


/**
 * @author van.shu
 * @create 2021/5/13 14:04
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Data
@Slf4j
public abstract class UmsResponse implements Response {

    private static final String HEAD = "resp";

    private RespHead resp;

    public JSONObject parseRespHead() throws JSONException {

        String json = JSON.toJSONString(this);

        JSONObject jsonObject = JSONObject.parseObject(json);

        JSONObject headObj = (JSONObject) jsonObject.remove(HEAD);

        log.debug("headObj = {}", headObj);

        JSON.parseObject(headObj.toJSONString(), RespHead.class);

        return jsonObject;

    }

    @Override
    public <T> T acquireData(Class<T> clazz) throws JSONException {
        JSONObject jsonObject = parseRespHead();
        return jsonObject.toJavaObject(clazz);

    }

    @Override
    public Integer getSsid() {
        return resp.getSsid();
    }

    public Integer getSsno() {
        return resp.getSsno();
    }

    public Integer getErrcode() {
        return resp.getErrcode();
    }

}
