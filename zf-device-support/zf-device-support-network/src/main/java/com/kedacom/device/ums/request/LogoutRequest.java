package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Data
public class LogoutRequest extends BaseRequest {

    private static final String COMMAND = "logout";

    @Override
    public String name() {
        return COMMAND;
    }
}
