package com.kedacom.device.core.convert;

import com.kedacom.device.mp.mcu.request.*;
import com.kedacom.device.mp.mcu.response.*;
import com.kedacom.mp.mcu.entity.UmsMcuEntity;
import com.kedacom.mp.mcu.request.*;
import com.kedacom.mp.mcu.response.*;
import org.mapstruct.Mapper;

/**
 * @author hxj
 * @date: 2021/8/13 13:46
 * @description mcu参数转换接口
 */
@Mapper(componentModel = "spring")
public interface McuConvert {

    McuLoginRequest login(UmsMcuEntity entity);

    McuAccountRequest account(McuAccountDTO dto);

    McuConfsRequest confs(McuConfsDTO dto);

    McuConfsVO accountRes(McuConfsResponse confsResponse);

    McuTemplatesRequest templates(McuTemplatesDTO dto);

    McuConfsVO templatesRes(McuTemplatesResponse response);

    McuConfInfoRequest confinfo(McuConfInfoDTO dto);

    McuConfInfoVO confinfoRes(McuConfInfoResponse response);

    McuConfVO confRes(McuConfResponse response);

    McuConfRequest conf(McuConfDTO dto);

    McuConfTemplateRequest confTemplate(McuConfTemplateDTO dto);

    McuMtMembersRequest mtMembers(McuMtMembersDTO dto);

    McuMtmembersVO mtMembersRes(McuMtmembersResponse response);

    McuMtRequest mt(McuMtDTO dto);

    McuMtCallRequest mtCall(McuMtCallDTO dto);

    McuSpeakerRequest speaker(McuSpeakerDTO dto);

    McuChairmanRequest chairman(McuChairmanDTO dto);

    McuSilenceRequest silence(McuSilenceDTO dto);

    McuMuteRequest mute(McuMuteDTO dto);

    McuVolumeRequest volume(McuVolumeDTO dto);

    McuDualRequest dual(McuDualDTO dto);

    McuAudioMixRequest audioMix(McuAudioMixDTO dto);

    McuAudioMixMemberRequest audioMixMember(McuAudioMixMemberDTO dto);

    McuRecRequest rec(McuRecDTO dto);

    McuRecVO recRes(McuRecResponse response);

    McuTvWallsVO tvWallsRes(McuTvWallsResponse response);

    McuTvWallRequest tvwall(McuTvWallDTO dto);

    McuExchangeRequest exchange(McuExchangeDTO dto);

    McuMessageRequest message(McuMessageDTO dto);

    McuVideoMixRequest videoMix(McuVideoMixDTO dto);

    McuMtVO mtRes(McuMtResponse response);

    McuAudioMixMemberVO audioMixMemberRes(McuAudioMixMemberResponse response);

    ConfTemplateVo convertToConfTemplateVo(ConfTemplateResponse response);

}
