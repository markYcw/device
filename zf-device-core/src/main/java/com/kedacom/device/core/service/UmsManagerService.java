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
     * 统一平台IP判重
     * @param id
     * @param deviceIp
     * @return
     */
    Boolean isRepeatForDeviceIp(String id, String deviceIp);

    /**
     * 统一平台端口判重
     * @param id
     * @param devicePort
     * @return
     */
    Boolean isRepeatForDevicePort(String id, Integer devicePort);

    /**
     * 删除统一平台信息
     * @param requestDto
     * @return
     */
    Boolean deleteUmsDevice(UmsDeviceInfoDeleteRequestDto requestDto);

    /**
     * 手动同步设备数据
     * @param requestDto
     * @return
     */
    void syncDeviceData(UmsDeviceInfoSyncRequestDto requestDto);

    /**
     * 获取当前服务统一设备最近一次同步设备时间
     * @param requestDto
     * @return
     */
    String getUmsLastSyncTime(UmsDeviceInfoSyncRequestDto requestDto);

    /**
     * 查询统一平台信息
     * @param requestDto
     * @return
     */
    BasePage<UmsDeviceInfoSelectResponseDto> selectUmsDeviceList(UmsDeviceInfoSelectRequestDto requestDto);

    /**
     * 查询平台下挂载的子设备信息
     * @param requestDto
     * @return
     */
    BasePage<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceList(UmsSubDeviceInfoQueryRequestDto requestDto);

    /**
     * 查询当前分组下挂载的子设备信息
     * @param requestDto
     * @return
     */
    List<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceByGroupId (UmsSubDeviceInfoQueryByGroupIdRequestDto requestDto);

    /**
     * 根据设备id查询设备信息
     * @param requestDto
     * @return
     */
    List<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceByIds(UmsSubDeviceInfoQueryByIdsRequestDto requestDto);

    /**
     * 根据国标id查询子设备信息
     * @param requestDto
     * @return
     */
    List<UmsSubDeviceInfoQueryResponseDto> selectUmsSubDeviceByGbIds(UmsSubDeviceInfoQueryByGbIdsRequestDto requestDto);

    /**
     * 删除统一平台下的子设备
     * @param requestDto
     * @return
     */
    Boolean deleteUmsSubDevice(UmsSubDeviceInfoDeleteRequestDto requestDto);

    /**
     * 查询告警类型信息
     * @return
     */
    List<UmsAlarmTypeQueryResponseDto> selectUmsAlarmTypeList();

    /**
     * 查询统一设备平台分组集合信息接口
     * @param requestDto
     * @return
     */
    List<UmsScheduleGroupItemQueryResponseDto> selectUmsGroupList(UmsScheduleGroupQueryRequestDto requestDto);

    /**
     * 根据当前分组ID查询子分组集合
     * @param requestDto
     * @return
     */
    List<SelectChildUmsGroupResponseDto> selectChildUmsGroupList(SelectChildUmsGroupRequestDto requestDto);

    /**
     * 根据子设备条件查询统一平台下分组中设备状况信息
     * @param requestDto
     * @return
     */
    List<UmsScheduleGroupItemQueryResponseDto> selectUmsGroupItemList(UmsScheduleGroupItemQueryRequestDto requestDto);

    /**
     * 获取所有设备分组
     * @param umsId
     * @return
     */
    Boolean queryDeviceGroupNotify(String umsId);

}
