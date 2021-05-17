package com.kedacom.device.ums.response;

import com.alibaba.fastjson.JSONException;
import com.kedacom.core.pojo.BaseResponse;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/15
 */
@Data
@ToString(callSuper = true)
public class JoinDiscussionGroupResponse extends BaseResponse {

    private List<String> DeviceIDs;

    @Override
    public <T> T acquireData(Class<T> clazz)throws JSONException {

        return super.acquireData(clazz);
    }

}
