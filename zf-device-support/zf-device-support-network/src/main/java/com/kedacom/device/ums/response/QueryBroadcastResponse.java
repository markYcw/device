package com.kedacom.device.ums.response;

import com.alibaba.fastjson.JSONException;
import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.ums.responsedto.MediaVo;
import lombok.Data;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/14
 */
@Data
@ToString(callSuper = true)
public class QueryBroadcastResponse extends BaseResponse {

    private String GroupID;

    private MediaVo Broadcast;

    @Override
    public <T> T acquireData(Class<T> clazz)throws JSONException {

        return super.acquireData(clazz);
    }

}
