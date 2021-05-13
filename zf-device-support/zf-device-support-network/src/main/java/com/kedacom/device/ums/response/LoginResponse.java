package com.kedacom.device.ums.response;

import com.alibaba.fastjson.JSONException;
import lombok.Data;
import lombok.ToString;


/**
 * @author van.shu
 * @create 2021/5/13 14:15
 */
@Data
@ToString(callSuper = true)
public class LoginResponse extends UmsResponse {

    @Override
    public <T> T acquireData(String json, Class<T> clazz)throws JSONException {

        return super.acquireData(json, clazz);
    }


}
