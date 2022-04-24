package com.kedacom.device.core.service;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.UmsDeviceInfoSelectByIdResponseDto;
import com.kedacom.ums.responsedto.UmsDeviceInfoSelectResponseDto;

import java.util.concurrent.ExecutionException;

/**
 * @author ycw
 * @describe
 * @date 2022/04/02
 */
public interface NewMediaService {

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

}
