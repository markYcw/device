package com.kedacom.acl.network.unite;

import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.DisplayControlBaseDTO;
import com.kedacom.avIntegration.request.DisplayControlLoginRequest;
import com.kedacom.avIntegration.response.DisplayControlLoginResponse;
import com.kedacom.avIntegration.response.DisplayControlVersionResponse;

/**
 * 音视融合（拼控）服务 接口
 *
 * @author van.shu
 * @create 2021/4/26 18:06
 */
public interface AvIntegrationInterface {

    /**
     * 登录显控统一服务，获取令牌
     *
     * @param requestDTO
     * @return AvIntegrationLoginResponseVO
     */
    BaseResult<DisplayControlLoginResponse> login(DisplayControlLoginRequest requestDTO);

    /**
     * 成功登陆后，用户有效期为30m，需手动调用该接口保持心跳
     *
     * @param baseDTO
     * @return BaseResult
     */
    BaseResult keepalive(DisplayControlBaseDTO baseDTO);

    /**
     * 显控统一服务API版本号
     *
     * @param baseDTO
     * @return DisplayControlVersionResponse
     */
    BaseResult<DisplayControlVersionResponse> version(DisplayControlBaseDTO baseDTO);

    /**
     * 退出显控统一服务，关闭令牌
     *
     * @param baseDTO
     * @return
     */
    BaseResult logout(DisplayControlBaseDTO baseDTO);

}
