package com.kedacom.device.core.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.common.utils.PinYinUtils;
import com.kedacom.device.core.constant.UmsMod;
import com.kedacom.device.core.convert.UmsSubDeviceConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.entity.SubDeviceInfoEntity;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.mapper.SubDeviceMapper;
import com.kedacom.device.ums.RepeatDeviceRequest;
import com.kedacom.device.ums.UmsClient;
import com.kedacom.device.ums.request.QueryDeviceRequest;
import com.kedacom.device.ums.response.QuerySubDeviceInfoResponse;
import com.kedacom.ums.responsedto.SubDeviceInfoResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.kedacom.device.core.constant.DeviceConstants.REQUEST2;

/**
 * @author van.shu
 * @create 2020/9/2 17:53
 */
@Component
@Slf4j
public class UmsSubDeviceManager extends ServiceImpl<SubDeviceMapper, SubDeviceInfoEntity> {

    @Autowired
    private SubDeviceMapper subDeviceInfoMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private UmsClient umsClient;

    public Integer selectAndInsertSubDeviceFromAvFeign(String umsDeviceId, Integer curPage, Integer pageSize) {

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(umsDeviceId);
        String sessionId = deviceInfoEntity.getSessionId();
        QueryDeviceRequest requestVo = new QueryDeviceRequest();
        requestVo.setSsid(Integer.valueOf(sessionId));
        requestVo.setQueryindex(curPage);
        requestVo.setQuerycount(pageSize);
        QuerySubDeviceInfoResponse responseVo = umsClient.querydev(requestVo);

        // 如果当前请求异常， 将重新发送获取设备的请求（最多再请求两次）
        RepeatDeviceRequest repeatDeviceRequest = repeatRequest(responseVo, requestVo);
        if (repeatDeviceRequest.getRequestResult()) {
            responseVo = repeatDeviceRequest.getResponse();
        } else {
            // 请求响应失败，记录第几页失败，当前页的请求数量
            log.error("同步设备第{}页异常，请求同步数据为{}条, 异常码为{}", curPage, pageSize, repeatDeviceRequest.getErrCode());
        }
        List<SubDeviceInfoResponseVo> responseVoList = responseVo.getDevinfo();
        log.info("当前第{}页查询到的数据条数是{}", curPage, responseVoList.size());
        if (CollUtil.isEmpty(responseVoList)) {
            return Integer.MAX_VALUE;
        }
        List<SubDeviceInfoEntity> umsSubDeviceInfoEntityList = UmsSubDeviceConvert.INSTANCE.convertSubDeviceInfoEntityList(responseVoList);
        log.info("当前第{}页转化后得到的数据条数是{}", curPage, umsSubDeviceInfoEntityList.size());
        umsSubDeviceInfoEntityList.forEach(x -> x.setDeviceMod(UmsMod.NORMAL));
        for (SubDeviceInfoEntity umsSubDeviceInfoEntity : umsSubDeviceInfoEntityList) {
            if (umsSubDeviceInfoEntity != null && StrUtil.isNotBlank(umsSubDeviceInfoEntity.getId())) {
                String name = umsSubDeviceInfoEntity.getName();
                String hanZiPinYin = PinYinUtils.getHanZiPinYin(name);
                String hanZiInitial = PinYinUtils.getHanZiInitials(name);
                String lowerCase = PinYinUtils.StrToLowerCase(hanZiInitial);
                umsSubDeviceInfoEntity.setPinyin(hanZiPinYin + "&&" + lowerCase);
                umsSubDeviceInfoEntity.setParentId(umsDeviceId);
                umsSubDeviceInfoEntity.setInstallDate(null);
                LambdaQueryWrapper<SubDeviceInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SubDeviceInfoEntity::getDeviceId, umsSubDeviceInfoEntity.getId())
                        .eq(SubDeviceInfoEntity::getParentId, umsDeviceId);
                SubDeviceInfoEntity subDeviceInfoEntity = subDeviceInfoMapper.selectOne(queryWrapper);
                if (subDeviceInfoEntity != null) {
                    umsSubDeviceInfoEntity.setId(subDeviceInfoEntity.getId());
                    int i = subDeviceInfoMapper.updateById(umsSubDeviceInfoEntity);
                    if (i <= 0) {
                        log.error("设备id:{},更新失败", umsSubDeviceInfoEntity.getId());
                    }
                } else {
                    umsSubDeviceInfoEntity.setId(null);
                    int insert = subDeviceInfoMapper.insert(umsSubDeviceInfoEntity);
                    if (insert <= 0) {
                        log.error("设备id:{},插入失败", umsSubDeviceInfoEntity.getId());
                    }
                }
            }
        }

        return 0;
    }

    public void updateUmsSubDeviceMod(Integer ordinaryMod, Integer updateMod) {

        // subDeviceInfoMapper.updateMod(ordinaryMod, updateMod);

        UpdateWrapper<SubDeviceInfoEntity> updateWrapper = new UpdateWrapper();
        LambdaUpdateWrapper<SubDeviceInfoEntity> lambda = updateWrapper.lambda();

        lambda.set(SubDeviceInfoEntity::getDeviceMod, updateMod);
        lambda.eq(SubDeviceInfoEntity::getDeviceMod, ordinaryMod);

        subDeviceInfoMapper.update(null, updateWrapper);
    }

    public RepeatDeviceRequest repeatRequest(QuerySubDeviceInfoResponse responseVo, QueryDeviceRequest requestVo) {

        RepeatDeviceRequest repeatDeviceRequest = new RepeatDeviceRequest();
        Integer errCode = responseVo.acquireErrcode();
        if (errCode == 0) {
            repeatDeviceRequest.setRequestResult(true);
            repeatDeviceRequest.setResponse(responseVo);
            return repeatDeviceRequest;
        }
        // 请求结果失败，重新发送请求（最多请求两次）
        for (int i = 0; i < REQUEST2; i++) {
            responseVo = umsClient.querydev(requestVo);
            // 如果请求响应成功，返回结果
            if (responseVo.acquireErrcode() == 0) {
                repeatDeviceRequest.setRequestResult(true);
                repeatDeviceRequest.setResponse(responseVo);
                return repeatDeviceRequest;
            }
        }
        repeatDeviceRequest.setRequestResult(false);
        repeatDeviceRequest.setErrCode(responseVo.acquireErrcode());

        return repeatDeviceRequest;
    }

}
