package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kedacom.BasePage;
import com.kedacom.aiBox.request.*;
import com.kedacom.aiBox.response.AiBoxContrastResponseDto;
import com.kedacom.aiBox.response.GetThresholdResponseDto;
import com.kedacom.aiBox.response.QueryListResponseDto;
import com.kedacom.aiBox.response.SelectPageResponseDto;
import com.kedacom.common.utils.PinYinUtils;
import com.kedacom.device.core.convert.AiBoxConvert;
import com.kedacom.device.core.entity.AiBoxEntity;
import com.kedacom.device.core.entity.AiBoxThresholdEntity;
import com.kedacom.device.core.exception.AiBoxException;
import com.kedacom.device.core.mapper.AiBoxMapper;
import com.kedacom.device.core.mapper.AiBoxThresholdMapper;
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

    @Resource
    AiBoxThresholdMapper aiBoxThresholdMapper;

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

        List<SelectPageResponseDto> selectPageResponseDtoList = selectPageResponseDtoList(records);
        BasePage<SelectPageResponseDto> basePage = new BasePage<>();
        basePage.setTotal(entityPage.getTotal());
        basePage.setData(selectPageResponseDtoList);
        basePage.setTotalPage(entityPage.getPages());
        basePage.setCurPage(requestDto.getCurPage());
        basePage.setPageSize(requestDto.getPageSize());

        return basePage;
    }

    public List<SelectPageResponseDto> selectPageResponseDtoList(List<AiBoxEntity> aiBoxList) {

        List<SelectPageResponseDto> selectPageResponseDtoList = AiBoxConvert.INSTANCE.convertSelectPageResponseDtoList(aiBoxList);
        if (CollectionUtil.isNotEmpty(selectPageResponseDtoList)) {
            List<AiBoxThresholdEntity> aiBoxThresholdEntityList = aiBoxThresholdMapper.selectList(null);
            out : for (SelectPageResponseDto responseDto : selectPageResponseDtoList) {
                for (AiBoxThresholdEntity aiBoxThresholdEntity : aiBoxThresholdEntityList) {
                    if (responseDto.getId().equals(aiBoxThresholdEntity.getAbId())) {
                        responseDto.setAbMinFace(aiBoxThresholdEntity.getAbMinFace());
                        responseDto.setAbMaxFace(aiBoxThresholdEntity.getAbMaxFace());
                        continue out;
                    }
                }
            }
        }

        return selectPageResponseDtoList;
    }

    @Override
    public List<QueryListResponseDto> queryList(QueryListRequestDto requestDto) {

        log.info("查询AI-Box设备信息集合请求参数 : {}", requestDto);
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

        return result;
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
            aiBoxMapper.insert(aiBoxEntity);
            getThreshold(aiBoxEntity, false);
            type = "添加";
        } else {
            // 修改AI-Box设备信息
            AiBoxEntity entityById = aiBoxMapper.selectById(aiBoxEntity.getId());
            if (!aiBoxEntity.getAbIp().equals(entityById.getAbIp())) {
                getThreshold(aiBoxEntity, false);
            }
            aiBoxMapper.updateById(aiBoxEntity);
            type = "修改";
        }

        return type;
    }

    @Override
    public boolean delete(DeleteRequestDto requestDto) {

        List<String> ids = requestDto.getIds();
        log.info("删除AI-Box设备信息请求参数 : [{}]", ids);
        // 重置设备的默认阈值
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (String id : ids) {
                    resetThreshold(id);
                }
            }
        });
        // 删除AIBox设备信息
        int deleteAiBoxBatchIds = aiBoxMapper.deleteBatchIds(ids);
        // 删除AIBox阈值信息
        LambdaQueryWrapper<AiBoxThresholdEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(AiBoxThresholdEntity::getAbId, ids);
        int deleteAiBoxThresholdBatchIds = aiBoxThresholdMapper.delete(queryWrapper);

        return ids.size() == deleteAiBoxBatchIds && deleteAiBoxBatchIds == deleteAiBoxThresholdBatchIds;
    }

    @Override
    public GetThresholdResponseDto getThresholdOfInterface(GetThresholdRequestDto requestDto) {

        String abId = requestDto.getAbId();
        AiBoxEntity aiBoxEntity = aiBoxMapper.selectById(abId);
        if (aiBoxEntity == null) {
            return null;
        }

        return getThreshold(aiBoxEntity, true);
    }

    @Override
    public boolean setThreshold(SetThresholdRequestDto requestDto) {

        String abId = requestDto.getAbId();
        Integer minFace = requestDto.getMinFace();
        Integer maxFace = requestDto.getMaxFace();
        log.info("设置AIBox设备人脸对比阈值请求参数 : {}", requestDto);
        String value = checkFaceValue(minFace, maxFace);
        if (StrUtil.isNotBlank(value)) {
            log.error("设置AIBox设备人脸对比阈值失败 : {}", value);
            throw new AiBoxException(value);
        }
        // 更新远端设备的阈值
        AiBoxEntity aiBoxEntity = aiBoxMapper.selectById(abId);
        updateThreshold(aiBoxEntity, minFace, maxFace);

        LambdaUpdateWrapper<AiBoxThresholdEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AiBoxThresholdEntity::getAbId, abId)
                .set(AiBoxThresholdEntity::getAbMinFace, minFace)
                .set(AiBoxThresholdEntity::getAbMaxFace, maxFace);
        int update = aiBoxThresholdMapper.update(null, updateWrapper);

        return update > 0;
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

    public String checkFaceValue(Integer minFace, Integer maxFace) {

        if (minFace >= maxFace) {
            return "最大像素必须大于最小像素，请重新填写";
        }
        if (minFace == 0) {
            return "最小像素值有误，请重新填写";
        }
        if (maxFace == 0) {
            return "最大像素值有误，请重新填写";
        }

        return null;
    }

    public GetThresholdResponseDto getThreshold(AiBoxEntity aiBoxEntity, boolean flag) {

        Integer status = 0;
        String abIp = aiBoxEntity.getAbIp();
        Integer abPort = aiBoxEntity.getAbPort();
        String username = aiBoxEntity.getAbUsername();
        String password = aiBoxEntity.getAbPassword();
        String url = "http://" + abIp + ":" + abPort + "/NVR/GetFaceCompareAlgCfg";
        log.info("获取人脸识别算法参数配置请求url : {}", url);

        String responseStr = AiBoxHttpDigest.doPostDigest(url, username, password, JSON.toJSONString(new AiBoxGetThresholdRequestDto()));
        log.info("responseStr : {}", responseStr);
        JSONObject jsonObject = JSON.parseObject(responseStr);
        Integer code  = (Integer) jsonObject.get("StatusCode");
        if (!status.equals(code)) {
            log.error("获取人脸识别算法参数配置失败！");
            throw new AiBoxException("获取人脸识别算法参数配置失败！");
        }
        Object minFace = jsonObject.get("MinFace");
        Object maxFace = jsonObject.get("MaxFace");
        if (!flag) {
            AiBoxThresholdEntity aiBoxThresholdEntity = new AiBoxThresholdEntity();
            aiBoxThresholdEntity.setAbId(aiBoxEntity.getId());
            aiBoxThresholdEntity.setAbMinFace((Integer) minFace);
            aiBoxThresholdEntity.setAbMaxFace((Integer) maxFace);

            aiBoxThresholdMapper.insert(aiBoxThresholdEntity);
            return null;
        }
        GetThresholdResponseDto responseDto = new GetThresholdResponseDto();
        responseDto.setAbIp(abIp);
        responseDto.setMinFace((String) minFace);
        responseDto.setMaxFace((String) maxFace);
        responseDto.setAbName(aiBoxEntity.getAbName());

        return responseDto;
    }

    public void updateThreshold(AiBoxEntity aiBoxEntity, Integer abMinFace, Integer abMaxFace) {

        Integer status = 0;
        String abIpDto = aiBoxEntity.getAbIp();
        Integer abPort = aiBoxEntity.getAbPort();
        String username = aiBoxEntity.getAbUsername();
        String password = aiBoxEntity.getAbPassword();
        String url = "http://" + abIpDto + ":" + abPort + "/NVR/SetFaceCompareAlgCfg";
        log.info("置人脸识别算法参数配置请求url : {}", url);
        AiBoxUpdateThresholdRequestDto requestDto = new AiBoxUpdateThresholdRequestDto();
        requestDto.setMinFace(abMinFace);
        requestDto.setMaxFace(abMaxFace);

        String responseStr = AiBoxHttpDigest.doPostDigest(url, username, password, JSON.toJSONString(requestDto));
        log.info("responseStr : {}", responseStr);
        JSONObject jsonObject = JSON.parseObject(responseStr);
        Integer code  = (Integer) jsonObject.get("StatusCode");
        if (!status.equals(code)) {
            log.error("设置人脸识别算法参数配置失败！");
            throw new AiBoxException("设置人脸识别算法参数配置失败！");
        }

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
