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
import com.kedacom.device.core.exception.AiBoxException;
import com.kedacom.device.core.mapper.AiBoxMapper;
import com.kedacom.device.core.service.AiBoxService;
import com.kedacom.device.core.utils.AiBoxHttpDigest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    ExecutorService executorService = Executors.newCachedThreadPool();

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
            queryWrapper.and(query -> query.like(AiBoxEntity::getAbName, name).or().like(AiBoxEntity::getAbPinyin, name));
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
            queryWrapper.and(query -> query.like(AiBoxEntity::getAbName, abName).or().like(AiBoxEntity::getAbPinyin, abName));
        }
        List<AiBoxEntity> aiBoxEntityList = aiBoxMapper.selectList(queryWrapper);

        return AiBoxConvert.INSTANCE.convertQueryListResponseDtoList(aiBoxEntityList);
    }

    @Override
    public String checkRequestDto(AddOrUpdateRequestDto requestDto) {

        String result;
        String id = requestDto.getId();
        LambdaQueryWrapper<AiBoxEntity> wrapper = new LambdaQueryWrapper<>();
        // 校验设备IP的唯一性
        result = checkIp(wrapper, id, requestDto.getAbIp());
        if (StrUtil.isNotBlank(result)) {
            return result;
        }
        wrapper.clear();
        // 校验设备名称的唯一性
        result = checkName(wrapper, id, requestDto.getAbName());
        if (StrUtil.isNotBlank(result)) {
            return result;
        }
        // 校验人脸识别像素值是否异常
        result = checkFaceValue(id, requestDto.getAbMinFace(), requestDto.getAbMaxFace());

        return result;
    }

    public String checkIp(LambdaQueryWrapper<AiBoxEntity> checkIpWrapper, String id, String ip) {

        checkIpWrapper.eq(AiBoxEntity::getAbIp, ip);
        if (StrUtil.isNotBlank(id)) {
            checkIpWrapper.ne(AiBoxEntity::getId, id);
        }
        AiBoxEntity aiBoxEntity = aiBoxMapper.selectOne(checkIpWrapper);
        if (aiBoxEntity != null) {
            return "设备IP重复，请重新填写";
        }

        return null;
    }

    public String checkName(LambdaQueryWrapper<AiBoxEntity> checkNameWrapper, String id, String name) {

        checkNameWrapper.eq(AiBoxEntity::getAbName, name);
        if (StrUtil.isNotBlank(id)) {
            checkNameWrapper.ne(AiBoxEntity::getId, id);
        }
        AiBoxEntity aiBoxEntity = aiBoxMapper.selectOne(checkNameWrapper);
        if (aiBoxEntity != null) {
            return "设备名称重复，请重新填写";
        }

        return null;
    }

    public String checkFaceValue(String id, Integer minFace, Integer maxFace) {

        if (StrUtil.isBlank(id)) {
            return null;
        }
        if (minFace >= maxFace) {
            return "最大像素必须大于最小像素，请重新填写";
        }
        // 针对设备修改的校验
        if (minFace == 0) {
            return "最小像素值有误，请重新填写";
        }
        if (maxFace == 0) {
            return "最大像素值有误，请重新填写";
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOrUpdate(AddOrUpdateRequestDto requestDto) {

        String type;
        log.info("新增/修改AI-Box设备信息请求参数类信息 : [{}]", requestDto);
        AiBoxEntity aiBoxEntity = AiBoxConvert.INSTANCE.convertAiBoxEntity(requestDto);
        // 设备名称转拼音
        String pinYinStr = PinYinUtils.getPinYinStr(aiBoxEntity.getAbName());
        aiBoxEntity.setAbPinyin(pinYinStr);
        if (StrUtil.isBlank(aiBoxEntity.getId())) {
            // 新增AI-Box设备信息
            aiBoxEntity.setAbMinFace(0);
            aiBoxEntity.setAbMaxFace(0);
            aiBoxMapper.insert(aiBoxEntity);
            getThreshold(aiBoxEntity);
            type = "添加";
        } else {
            // 修改AI-Box设备信息
            updateThreshold(aiBoxEntity);
            aiBoxMapper.updateById(aiBoxEntity);
            type = "修改";
        }

        return type;
    }

    public void updateThreshold(AiBoxEntity entityDto) {

        Integer status = 0;
        String abIpDto = entityDto.getAbIp();
        Integer abMinFaceDto = entityDto.getAbMinFace();
        Integer abMaxFaceDto = entityDto.getAbMaxFace();
        AiBoxEntity aiBoxEntity = aiBoxMapper.selectById(entityDto.getId());
        if (!aiBoxEntity.getAbIp().equals(abIpDto) || !aiBoxEntity.getAbMinFace().equals(abMinFaceDto)
                || !aiBoxEntity.getAbMaxFace().equals(abMaxFaceDto)) {
            Integer abPort = entityDto.getAbPort();
            String username = entityDto.getAbUsername();
            String password = entityDto.getAbPassword();
            String url = "http://" + abIpDto + ":" + abPort + "/NVR/SetFaceCompareAlgCfg";
            AiBoxUpdateThresholdRequestDto requestDto = new AiBoxUpdateThresholdRequestDto();
            requestDto.setMinFace(entityDto.getAbMinFace());
            requestDto.setMaxFace(entityDto.getAbMaxFace());

            String responseStr = AiBoxHttpDigest.doPostDigest(url, username, password, JSON.toJSONString(requestDto));
            log.info("responseStr : {}", responseStr);
            JSONObject jsonObject = JSON.parseObject(responseStr);
            Integer code  = (Integer) jsonObject.get("StatusCode");
            if (!status.equals(code)) {
                log.error("设置人脸识别算法参数配置失败！");
                throw new AiBoxException("设置人脸识别算法参数配置失败！");
            }
        }

    }

    public void getThreshold(AiBoxEntity aiBoxEntity) {

        Integer status = 0;
        String abIp = aiBoxEntity.getAbIp();
        Integer abPort = aiBoxEntity.getAbPort();
        String username = aiBoxEntity.getAbUsername();
        String password = aiBoxEntity.getAbPassword();
        String url = "http://" + abIp + ":" + abPort + "/NVR/GetFaceCompareAlgCfg";

        String responseStr = AiBoxHttpDigest.doPostDigest(url, username, password, JSON.toJSONString(new AiBoxGetThresholdRequestDto()));
        log.info("responseStr : {}", responseStr);
        JSONObject jsonObject = JSON.parseObject(responseStr);
        Integer code  = (Integer) jsonObject.get("StatusCode");
        if (!status.equals(code)) {
            log.error("获取人脸识别算法参数配置失败！");
            throw new AiBoxException("获取人脸识别算法参数配置失败！");
        }
        Integer minFace = (Integer) jsonObject.get("MinFace");
        Integer maxFace = (Integer) jsonObject.get("MaxFace");
        aiBoxEntity.setAbMinFace(minFace);
        aiBoxEntity.setAbMaxFace(maxFace);

        aiBoxMapper.updateById(aiBoxEntity);
    }

    @Override
    public boolean delete(DeleteRequestDto requestDto) {

        List<String> ids = requestDto.getIds();
        // 重置设备的默认阈值
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (String id : ids) {
                    resetThreshold(id);
                }
            }
        });
        int deleteBatchIds = aiBoxMapper.deleteBatchIds(ids);

        return ids.size() == deleteBatchIds;
    }

    public void resetThreshold(String id) {

        Integer status = 0;
        AiBoxEntity aiBoxEntity = aiBoxMapper.selectById(id);
        String url = "http://" + aiBoxEntity.getAbIp() + ":" + aiBoxEntity.getAbPort() + "/NVR/SetFaceCompareAlgCfg";
        AiBoxUpdateThresholdRequestDto requestDto = new AiBoxUpdateThresholdRequestDto();
        requestDto.setMinFace(60);
        requestDto.setMaxFace(400);

        String responseStr = AiBoxHttpDigest.doPostDigest(url, aiBoxEntity.getAbUsername(), aiBoxEntity.getAbPassword(), JSON.toJSONString(requestDto));
        log.info("responseStr : {}", responseStr);
        JSONObject jsonObject = JSON.parseObject(responseStr);
        Integer code  = (Integer) jsonObject.get("StatusCode");
        if (!status.equals(code)) {
            log.error("恢复人脸识别算法默认参数配置失败！");
            throw new AiBoxException("恢复人脸识别算法默认参数配置失败！");
        }

    }

    @Override
    public AiBoxContrastResponseDto contrast(ContrastRequestDto requestDto) {

        // AIBox接口暂时已知三种code，后续增加可创建类来做处理
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
        JSONObject jsonObject = JSON.parseObject(responseStr);
        AiBoxContrastResponseDto responseDto = new AiBoxContrastResponseDto();
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
            Integer similarity = (Integer) similarityMap.get("Similarity");
            if (similarity == 0) {
                log.error("图片对比相似度为0（查看阈值设置是否正确），图片对比失败！");
                responseDto.setErrorMessage("图片对比失败！");
                return responseDto;
            }
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
