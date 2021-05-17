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
public class QuerySilenceResponse extends BaseResponse {

    @ApiModelProperty(value = "状态（1:静音 0:不静音）")
    private Integer SilenceState;

    @Override
    public <T> T acquireData(Class<T> clazz)throws JSONException {

        return super.acquireData(clazz);
    }

}
