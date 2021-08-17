package com.kedacom.device.core.service;

import com.kedacom.BaseResult;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.request.*;

/**
 * @author hxj
 * @date: 2021/8/13 13:50
 * @description 会议平台业务接口
 */
public interface McuService {

    BaseResult login(McuRequestDTO dto);

    BaseResult logout(McuRequestDTO dto);

    BaseResult account(McuAccountDTO dto);

    BaseResult confs(McuConfsDTO dto);

    BaseResult templates(McuTemplatesDTO dto);

    BaseResult confinfo(McuConfInfoDTO dto);

    BaseResult conf(McuConfDTO dto);

    BaseResult confTemplate(McuConfTemplateDTO dto);

    BaseResult mtMembers(McuMtMembersDTO dto);

    BaseResult mt(McuMtDTO dto);

    BaseResult mtCall(McuMtCallDTO dto);

    BaseResult speaker(McuSpeakerDTO dto);

    BaseResult chairman(McuChairmanDTO dto);

    BaseResult silence(McuSilenceDTO dto);

    BaseResult mute(McuMuteDTO dto);

    BaseResult volume(McuVolumeDTO dto);

    BaseResult dual(McuDualDTO dto);

    BaseResult videoMix(McuVideoMixDTO dto);

    BaseResult intaudioMix(McuIntaudioMixDTO dto);

    BaseResult audioMix(McuAudioMixDTO dto);

    BaseResult audioMixMember(McuAudioMixMemberDTO dto);

    BaseResult rec(McuRecDTO dto);

    BaseResult tvWalls(McuRequestDTO dto);

    BaseResult tvwall(McuTvWallDTO dto);

    BaseResult exchange(McuExchangeDTO dto);

    BaseResult message(McuMessageDTO dto);

}
