package com.kedacom.device.core.service;

import com.kedacom.BasePage;
import com.kedacom.aiBox.request.*;
import com.kedacom.aiBox.response.AiBoxContrastResponseDto;
import com.kedacom.aiBox.response.QueryListResponseDto;
import com.kedacom.aiBox.response.SelectPageResponseDto;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/15
 */
public interface AiBoxService {

    /**
     * 分页查询AI-Box设备信息列表
     * @param requestDto
     * @return
     */
    BasePage<SelectPageResponseDto> selectPage(SelectPageRequestDto requestDto);

    /**
     * 查询AI-Box设备信息集合
     * @param requestDto
     * @return
     */
    List<QueryListResponseDto> queryList(QueryListRequestDto requestDto);

    /**
     * 新增/修改AI-Box设备信息请求参数设备名称，ip的唯一校验
     * @param requestDto
     * @return
     */
    String checkRequestDto(AddOrUpdateRequestDto requestDto);

    /**
     * 新增/修改AI-Box设备信息
     * @param requestDto
     * @return
     */
    String addOrUpdate(AddOrUpdateRequestDto requestDto);

    /**
     * 删除AI-Box设备信息
     * @param requestDto
     * @return
     */
    boolean delete(DeleteRequestDto requestDto);

    /**
     * 图片对比
     * @param requestDto
     * @return
     */
    AiBoxContrastResponseDto contrast(ContrastRequestDto requestDto);

}
