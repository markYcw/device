package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kedacom.BasePage;
import com.kedacom.aiBox.request.*;
import com.kedacom.aiBox.response.AiBoxContrastResponseDto;
import com.kedacom.aiBox.response.QueryListResponseDto;
import com.kedacom.aiBox.response.SelectPageResponseDto;
import com.kedacom.common.utils.PinYinUtils;
import com.kedacom.device.core.convert.AiBoxConvert;
import com.kedacom.device.core.entity.AiBoxEntity;
import com.kedacom.device.core.mapper.AiBoxMapper;
import com.kedacom.device.core.service.AiBoxService;
import com.kedacom.device.core.utils.AiBoxHttpDigest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        String id = requestDto.getId();
        // 校验设备IP的唯一性
        result = checkIp(id, requestDto.getAbIp());
        if (StrUtil.isNotBlank(result)) {
            return result;
        }
        // 校验设备名称的唯一性
        result = checkName(id, requestDto.getAbName());

        return result;
    }

    public String checkIp(String id, String ip) {

        LambdaQueryWrapper<AiBoxEntity> queryIpWrapper = new LambdaQueryWrapper<>();
        queryIpWrapper.eq(AiBoxEntity::getAbIp, ip);
        if (StrUtil.isNotBlank(id)) {
            queryIpWrapper.ne(AiBoxEntity::getId, id);
        }
        AiBoxEntity aiBoxEntity = aiBoxMapper.selectOne(queryIpWrapper);
        if (aiBoxEntity != null) {
            return "设备IP重复，请重新填写";
        }

        return null;
    }

    public String checkName(String id, String name) {

        LambdaQueryWrapper<AiBoxEntity> queryNameWrapper = new LambdaQueryWrapper<>();
        queryNameWrapper.eq(AiBoxEntity::getAbName, name);
        if (StrUtil.isNotBlank(id)) {
            queryNameWrapper.ne(AiBoxEntity::getId, id);
        }
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
    public AiBoxContrastResponseDto contrast(ContrastRequestDto requestDto) {

        Integer status = 0;
        Integer statusCode1 = 1000;
        Integer statusCode2 = 1006;
        String abId = requestDto.getAbId();
        AiBoxEntity aiBoxEntity = aiBoxMapper.selectById(abId);
        String abIp = aiBoxEntity.getAbIp();
        Integer abPort = aiBoxEntity.getAbPort();
        String username = aiBoxEntity.getAbUsername();
        String password = aiBoxEntity.getAbPassword();
        String url = "http://" + abIp + ":" + abPort + "/NVR/CompareSimilarity";
        AiBoxContrastRequestDto aiBoxContrastRequestDto = convertRequestDto(requestDto);

        String responseStr = AiBoxHttpDigest.doPostDigest(url, username, password, JSON.toJSONString(aiBoxContrastRequestDto));
        log.info("responseStr : {}", responseStr);
        AiBoxContrastResponseDto responseDto = new AiBoxContrastResponseDto();
        if (StrUtil.isBlank(responseStr)) {
            responseDto.setErrorMessage("连接服务失败！");
            return responseDto;
        }
        JSONObject jsonObject = JSON.parseObject(responseStr);
        Integer code  = (Integer) jsonObject.get("StatusCode");
        if (statusCode1.equals(code)) {
            responseDto.setErrorMessage("上传图片有误！");
            return responseDto;
        }
        if (statusCode2.equals(code)) {
            responseDto.setErrorMessage("实时图片未检测到人脸！");
            return responseDto;
        }
        if (status.equals(code)) {
            JSONArray similarityList = jsonObject.getJSONArray("SimilarityList");
            Map<String, Object> similarityMap = (Map<String, Object>) similarityList.get(0);
            responseDto.setFlag(true);
            responseDto.setData(String.valueOf(similarityMap.get("Similarity")));
            return responseDto;
        }
        responseDto.setErrorMessage("图片对比失败！");

        return responseDto;
    }

    private AiBoxContrastRequestDto convertRequestDto(ContrastRequestDto requestDto) {

        AiBoxContrastDto aiBoxContrastDto = new AiBoxContrastDto();
        aiBoxContrastDto.setID(requestDto.getId());
        aiBoxContrastDto.setName(requestDto.getName());
        aiBoxContrastDto.setFeatureLibImg(requestDto.getFeatureLibImg());
        List<AiBoxContrastDto> aiBoxContrastDtoList = new ArrayList<>();
        aiBoxContrastDtoList.add(aiBoxContrastDto);
        AiBoxContrastRequestDto request = new AiBoxContrastRequestDto();
        request.setRealTimeImg(requestDto.getRealTimeImg());
        request.setFeatureLibImgList(aiBoxContrastDtoList);
        request.setMsgId("");
        request.setNVRUuid("");
        request.setSessionId("");

        return request;
    }
}
