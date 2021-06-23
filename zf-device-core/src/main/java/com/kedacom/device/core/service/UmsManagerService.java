package com.kedacom.device.core.service;

import com.kedacom.BasePage;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
public interface UmsManagerService {

    /**
     * 添加统一平台信息
     * @param requestDto
     * @return
     */
    String insertUmsDevice(UmsDeviceInfoAddRequestDto requestDto);

    /**
     * 更新统一平台信息
     * @param requestDto
     * @return
     */
    String updateUmsDevice(UmsDeviceInfoUpdateRequestDto requestDto);

    /**
     * 统一平台名称判重
     * @param id
     * @param name
     * @return
     */
    Boolean isRepeatForName(String id, String name);

    /**
     * 统一平台 IP + port 判重
     * @param id
     * @param deviceIp
     * @param devicePort
     * @return
     */
    Boolean isRepeatForDeviceIpAndPort(String id, String deviceIp, Integer devicePort);

    /**
     * 删除统一平台信息
     * @param requestDto
     * @return
     */
    Boolean deleteUmsDevice(UmsDeviceInfoDeleteRequestDto requestDto);

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
