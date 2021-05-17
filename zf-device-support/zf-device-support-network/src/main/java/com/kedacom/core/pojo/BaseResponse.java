package com.kedacom.core.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author van.shu
 * @date 2021/5/17 1:21
 */
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse implements Response {

    private RespHead resp;

    private JSONObject removeHead() {

        String json = JSON.toJSONString(this);
        JSONObject jsonObject = JSON.parseObject(json);

        if (!jsonObject.containsKey("resp")) {
            throw new IllegalArgumentException("this object is not resp type");
        }
        jsonObject.remove("resp");
        return jsonObject;

    }

    public Integer getSsid(){
        return resp.getSsid();
    }

    public Integer acquireErrcode() {
        return resp.getErrorcode();
    }

    @Override
    public Integer acquireSsno() {
        return resp.getSsno();
    }

    @Override
    public String acquireName() {
        return resp.getName();
    }

    @Override
    public <T> T acquireData(Class<T> clazz) throws JSONException {
        JSONObject jsonObject = removeHead();
        return jsonObject.toJavaObject(clazz);
    }
}
