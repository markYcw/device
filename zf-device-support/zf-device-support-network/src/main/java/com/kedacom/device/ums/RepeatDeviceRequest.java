package com.kedacom.device.ums;

import com.kedacom.device.ums.response.QuerySubDeviceInfoResponse;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/6/3
 */
@Data
public class RepeatDeviceRequest implements Serializable {

    private Boolean requestResult;

    private QuerySubDeviceInfoResponse response;

    private Integer errCode;

}
