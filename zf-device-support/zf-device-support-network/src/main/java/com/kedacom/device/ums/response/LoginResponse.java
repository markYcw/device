package com.kedacom.device.ums.response;

import com.alibaba.fastjson.JSONException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @author van.shu
 * @create 2021/5/13 14:15
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class LoginResponse extends UmsResponse {

    @Override
    public <T> T parsePacket(String json,Class<T> clazz)throws JSONException {

        return super.parsePacket(json, clazz);
    }


}
