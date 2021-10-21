package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kedacom.BasePage;
import com.kedacom.aiBox.request.*;
import com.kedacom.aiBox.response.AiBoxContrastDto;
import com.kedacom.aiBox.response.AiBoxContrastResponseDto;
import com.kedacom.aiBox.response.QueryListResponseDto;
import com.kedacom.aiBox.response.SelectPageResponseDto;
import com.kedacom.common.utils.PinYinUtils;
import com.kedacom.device.core.convert.AiBoxConvert;
import com.kedacom.device.core.entity.AiBoxEntity;
import com.kedacom.device.core.exception.AiBoxException;
import com.kedacom.device.core.mapper.AiBoxMapper;
import com.kedacom.device.core.service.AiBoxService;
import com.kedacom.device.core.utils.RemoteRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/15
 */
@Slf4j
@Service
public class AiBoxServiceImpl implements AiBoxService {

    @Resource
    AiBoxMapper aiBoxMapper;

    @Resource
    RemoteRestTemplate remoteRestTemplate;

    @Override
    public BasePage<SelectPageResponseDto> selectPage(SelectPageRequestDto requestDto) {

        Page<AiBoxEntity> page = new Page<>();
        page.setSize(requestDto.getPageSize());
        page.setCurrent(requestDto.getCurPage());

        String ip = requestDto.getIp();
        String name = requestDto.getName();
        log.info("分页查询AI-Box设备信息列表请求参数信息, ip : {}, 设备名称 : {}", ip, name);

        LambdaQueryWrapper<AiBoxEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(ip)) {
            queryWrapper.like(AiBoxEntity::getAbIp, ip);
        }
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like(AiBoxEntity::getAbName, name);
        }
        queryWrapper.orderByAsc(AiBoxEntity::getCreateTime);
        Page<AiBoxEntity> entityPage = aiBoxMapper.selectPage(page, queryWrapper);
        List<AiBoxEntity> records = entityPage.getRecords();

        List<SelectPageResponseDto> selectPageResponseDtoList = AiBoxConvert.INSTANCE.convertSelectPageResponseDtoList(records);
        BasePage<SelectPageResponseDto> basePage = new BasePage<>();
        basePage.setTotal(entityPage.getTotal());
        basePage.setData(selectPageResponseDtoList);
        basePage.setTotalPage(entityPage.getPages());
        basePage.setCurPage(requestDto.getCurPage());
        basePage.setPageSize(requestDto.getPageSize());

        return basePage;
    }

    @Override
    public List<QueryListResponseDto> queryList(QueryListRequestDto requestDto) {

        LambdaQueryWrapper<AiBoxEntity> queryWrapper = new LambdaQueryWrapper<>();
        String id = requestDto.getId();
        if (StrUtil.isNotBlank(id)) {
            queryWrapper.eq(AiBoxEntity::getId, id);
        }
        String abIp = requestDto.getAbIp();
        if (StrUtil.isNotBlank(abIp)) {
            queryWrapper.eq(AiBoxEntity::getAbIp, abIp);
        }
        String abName = requestDto.getAbName();
        if (StrUtil.isNotBlank(abName)) {
            queryWrapper.like(AiBoxEntity::getAbName, abName);
        }
        List<AiBoxEntity> aiBoxEntityList = aiBoxMapper.selectList(queryWrapper);

        return AiBoxConvert.INSTANCE.convertQueryListResponseDtoList(aiBoxEntityList);
    }

    @Override
    public String checkRequestDto(AddOrUpdateRequestDto requestDto) {

        String result;
        LambdaQueryWrapper<AiBoxEntity> queryWrapper = new LambdaQueryWrapper<>();
        String id = requestDto.getId();
        if (StrUtil.isNotBlank(id)) {
            queryWrapper.eq(AiBoxEntity::getId, id);
        }
        // 校验设备IP的唯一性
        result = checkIp(queryWrapper, requestDto.getAbIp());
        if (StrUtil.isNotBlank(result)) {
            return result;
        }
        // 校验设备名称的唯一性
        result = checkName(queryWrapper, requestDto.getAbName());

        return result;
    }

    public String checkIp(LambdaQueryWrapper<AiBoxEntity> queryIpWrapper, String ip) {

        queryIpWrapper.eq(AiBoxEntity::getAbIp, ip);
        AiBoxEntity aiBoxEntity = aiBoxMapper.selectOne(queryIpWrapper);
        if (aiBoxEntity != null) {
            return "设备IP重复，请重新填写";
        }

        return null;
    }

    public String checkName(LambdaQueryWrapper<AiBoxEntity> queryNameWrapper, String name) {

        queryNameWrapper.eq(AiBoxEntity::getAbName, name);
        AiBoxEntity aiBoxEntity = aiBoxMapper.selectOne(queryNameWrapper);
        if (aiBoxEntity != null) {
            return "设备名称重复，请重新填写";
        }

        return null;
    }

    @Override
    public String addOrUpdate(AddOrUpdateRequestDto requestDto) {

        String type;
        log.info("新增/修改AI-Box设备信息请求参数类信息 : [{}]", requestDto);
        AiBoxEntity aiBoxEntity = AiBoxConvert.INSTANCE.convertAiBoxEntity(requestDto);
        // 设备名称转拼音
        String pinYinStr = PinYinUtils.getPinYinStr(aiBoxEntity.getAbName());
        aiBoxEntity.setAbPinyin(pinYinStr);
        if (StrUtil.isBlank(aiBoxEntity.getId())) {
            // 新增AI-Box设备信息
            aiBoxMapper.insert(aiBoxEntity);
            type = "添加";
        } else {
            // 修改AI-Box设备信息
            aiBoxMapper.updateById(aiBoxEntity);
            type = "修改";
        }

        return type;
    }

    @Override
    public boolean delete(DeleteRequestDto requestDto) {

        List<String> ids = requestDto.getIds();
        int deleteBatchIds = aiBoxMapper.deleteBatchIds(ids);

        return ids.size() == deleteBatchIds;
    }

    @Override
    public String contrast(ContrastRequestDto requestDto) {

        RestTemplate template = remoteRestTemplate.getRestTemplate();
        AiBoxEntity aiBoxEntity = aiBoxMapper.selectById(requestDto.getAbId());
//        AiBoxContrastRequestDto dto = convertAiBoxContrastRequestDto(requestDto);
        Map<String, Object> requestMap = convertRequestMap(requestDto);

        String url = "http://" + aiBoxEntity.getAbIp() + ":" + aiBoxEntity.getAbPort() + "/NVR/CompareSimilarity";
//        String resultStr = template.postForObject(url, JSON.toJSONString(dto), String.class);
        String resultStr = template.postForObject(url, requestMap, String.class);
        log.info("AIBox对比图片返回结果信息 : {}", resultStr);

        AiBoxContrastResponseDto responseDto = JSON.parseObject(resultStr, AiBoxContrastResponseDto.class);
        if (responseDto == null || responseDto.getStatusCode() != 0) {
            String mes = responseDto == null ? "返回结果不存在" : responseDto.getStatusString();
            throw new AiBoxException("获取AIBox对比图片返回结果失败 " + mes);
        }
        AiBoxContrastDto aiBoxContrastDto = responseDto.getSimilarityList().get(0);

        return String.valueOf(aiBoxContrastDto.getSimilarity());
    }

    public Map<String, Object> convertRequestMap(ContrastRequestDto requestDto) {

        Map<String, Object> requestMap = new HashMap<>(2);
        requestMap.put("RealTimeImg", requestDto.getRealTimeImg());
        List<Map<String, String>> mapList = new ArrayList<>(1);
        Map<String, String> map = new HashMap<>(3);
        map.put("Name", requestDto.getName());
        map.put("ID num", requestDto.getId());
        map.put("FeatureLibImg", requestDto.getFeatureLibImg());
        mapList.add(map);
        requestMap.put("FeatureLibImgList", mapList);

        return requestMap;
    }

    public AiBoxContrastRequestDto convertAiBoxContrastRequestDto(ContrastRequestDto requestDto) {

        List<AiBoxFeatureLibDto> featureLibDtoList = new ArrayList<>();
        AiBoxFeatureLibDto aiBoxFeatureLibDto = new AiBoxFeatureLibDto();
        aiBoxFeatureLibDto.setID(requestDto.getId());
        aiBoxFeatureLibDto.setName(requestDto.getName());
        aiBoxFeatureLibDto.setFeatureLibImg(requestDto.getFeatureLibImg());
        featureLibDtoList.add(aiBoxFeatureLibDto);
        AiBoxContrastRequestDto aiBoxContrastRequestDto = new AiBoxContrastRequestDto();
        aiBoxContrastRequestDto.setRealTimeImg(requestDto.getRealTimeImg());
        aiBoxContrastRequestDto.setFeatureLibImgList(featureLibDtoList);

        return aiBoxContrastRequestDto;
    }
}
