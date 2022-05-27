package com.kedacom.device.core.service;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.newMedia.dto.SVROrderDTO;
import com.kedacom.streamMedia.request.GetAudioCapDTO;
import com.kedacom.streamMedia.request.GetBurnStateDTO;
import com.kedacom.streamMedia.request.GetSvrAudioActStateDTO;
import com.kedacom.streamMedia.response.GetAudioCapVO;
import com.kedacom.streamMedia.response.GetBurnStateVO;
import com.kedacom.streamMedia.response.GetSvrAudioActStateVo;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author ycw
 * @describe
 * @date 2022/04/02
 */
public interface NewMediaService {

    void initNM();

    void logoutById(Integer id);

    /**
     * 添加统一平台信息
     * @param requestDto
     * @return
     */
    BaseResult<String> insertUmsDevice(UmsDeviceInfoAddRequestDto requestDto);

    /**
     * 更新统一平台信息
     * @param requestDto
     * @return
     */
    BaseResult<String> updateUmsDevice(UmsDeviceInfoUpdateRequestDto requestDto);

    /**
     * 删除统一平台信息
     * @param requestDto
     * @return
     */
    BaseResult<String> deleteUmsDevice(UmsDeviceInfoDeleteRequestDto requestDto);

    /**
     * 查询统一平台信息
     * @param requestDto
     * @return
     */
    BasePage<UmsDeviceInfoSelectResponseDto> selectUmsDeviceList(UmsDeviceInfoSelectRequestDto requestDto);

    /**
     * 根据id查询统一平台信息
     * @param requestDto
     * @return
     */
    UmsDeviceInfoSelectByIdResponseDto getDeviceInfoById(UmsDeviceInfoSelectByIdRequestDto requestDto);

    void syncDeviceData(UmsDeviceInfoSyncRequestDto requestDto);

    BasePage<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceList(UmsSubDeviceInfoQueryRequestDto requestDto);

    List<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceByGroupId(UmsSubDeviceInfoQueryByGroupIdRequestDto requestDto);

    List<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceByGbIds(UmsSubDeviceInfoQueryByGbIdsRequestDto requestDto);

    List<UmsScheduleGroupItemQueryResponseDto> selectUmsGroupList(UmsScheduleGroupQueryRequestDto requestDto);

    List<SelectChildUmsGroupResponseDto> selectChildUmsGroupList(SelectChildUmsGroupRequestDto requestDto);

    Boolean sendOrderData(SVROrderDTO dto);

    GetAudioCapVO getAudioCap(GetAudioCapDTO getAudioCapDTO);

    GetSvrAudioActStateVo getSvrAudioActState(GetSvrAudioActStateDTO dto);

    GetBurnStateVO getBurnState(GetBurnStateDTO getBurnStateDTO);
}
