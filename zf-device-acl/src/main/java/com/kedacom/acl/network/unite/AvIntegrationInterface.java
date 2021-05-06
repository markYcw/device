package com.kedacom.acl.network.unite;

import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.*;
import com.kedacom.avIntegration.response.*;

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

    /**
     * 获取所有大屏配置
     *
     * @param request
     * @return TvWallListResponse
     */
    BaseResult<TvWallListResponse> ls(TvWallListRequest request);

    /**
     * 获取大屏布局，不等分模式下使用
     *
     * @param request
     * @return TvWallLayoutResponse
     */
    BaseResult<TvWallLayoutResponse> layout(TvWallLayoutRequest request);

    /**
     * 查询大屏通道绑定（虚拟屏）
     * 主要是查询虚拟屏窗口绑定关系信息和解码通道使用模式（使用显控平台的内置解码通道资源或外部绑定的解码通道资源）
     *
     * @param request
     * @return
     */
    BaseResult<TvWallQueryPipelineResponse> query(TvWallQueryPipelineRequest request);

    /**
     * 配置大屏通道绑定（虚拟屏）
     * 配置虚拟屏窗口与资源的绑定关系、解码通道资源使用模式
     *
     * @param request
     * @return
     */
    BaseResult<TvWallPipelineBindResponse> configBind(TvWallPipelineBindRequest request);

    /**
     * 设置大屏（虚拟屏）
     * 配置虚拟屏
     *
     * @param request
     * @return
     */
    BaseResult<TvWallConfigResponse> config(TvWallConfigRequest request);

    /**
     * 删除大屏（虚拟屏）
     * 拼接屏模式不允许删除，虚拟屏模式全部删除，混合屏模式删除绑定关系
     *
     * @param request
     * @return
     */
    BaseResult delete(TvWallDeleteRequest request);
}
