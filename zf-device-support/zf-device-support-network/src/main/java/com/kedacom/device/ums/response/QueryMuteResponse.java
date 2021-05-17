package com.kedacom.device.ums.response;

import com.alibaba.fastjson.JSONException;
import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/15
 */
@Data
@ToString(callSuper = true)
public class QueryMuteResponse extends BaseResponse {

    @ApiModelProperty(value = "状态（1:哑音 0:不哑音）")
    private Integer MuteState;

    @Override
    public <T> T acquireData(Class<T> clazz)throws JSONException {

        return super.acquireData(clazz);
    }

}
