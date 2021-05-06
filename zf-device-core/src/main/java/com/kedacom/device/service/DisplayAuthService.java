package com.kedacom.device.service;

import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.DisplayControlBaseDTO;
import com.kedacom.avIntegration.request.DisplayControlLoginRequest;
import com.kedacom.avIntegration.response.DisplayControlLoginResponse;
import com.kedacom.avIntegration.response.DisplayControlVersionResponse;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:33
 */

public interface DisplayAuthService {

    BaseResult<DisplayControlLoginResponse> login(DisplayControlLoginRequest request);

    BaseResult keepalive(DisplayControlBaseDTO baseDTO);

    BaseResult<DisplayControlVersionResponse> version(DisplayControlBaseDTO baseDTO);

    BaseResult logout(DisplayControlBaseDTO baseDTO);

}
