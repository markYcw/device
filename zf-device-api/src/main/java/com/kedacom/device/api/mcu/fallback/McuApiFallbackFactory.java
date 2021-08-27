package com.kedacom.device.api.mcu.fallback;

import com.kedacom.BaseResult;
import com.kedacom.device.api.mcu.McuApi;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.request.*;
import com.kedacom.mp.mcu.response.*;
import feign.hystrix.FallbackFactory;

/**
 * @Author hxj
 * @Date: 2021/8/12 13:41
 * @Description 会议平台api熔断类
 */
public class McuApiFallbackFactory implements FallbackFactory<McuApi> {
    @Override
    public McuApi create(Throwable throwable) {
        return new McuApi() {
            @Override
            public BaseResult<McuLoginVO> login(McuRequestDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult logout(McuRequestDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult account(McuAccountDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<McuConfsVO> confs(McuConfsDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<McuConfsVO> templates(McuTemplatesDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<McuConfInfoVO> confinfo(McuConfInfoDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<McuConfVO> conf(McuConfDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult confTemplate(McuConfTemplateDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<McuMtmembersVO> mtMembers(McuMtMembersDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<McuMtVO> mt(McuMtDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult mtCall(McuMtCallDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult speaker(McuSpeakerDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult chairman(McuChairmanDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult silence(McuSilenceDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult mute(McuMuteDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult volume(McuVolumeDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult dual(McuDualDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult videoMix(McuVideoMixDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult audioMix(McuAudioMixDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<McuAudioMixMemberVO> audioMixMember(McuAudioMixMemberDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult rec(McuRecDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<McuTvWallsVO> tvWalls(McuRequestDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult tvwall(McuTvWallDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult exchange(McuExchangeDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult message(McuMessageDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }
        };
    }
}