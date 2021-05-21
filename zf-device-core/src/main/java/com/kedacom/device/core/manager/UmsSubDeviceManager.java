package com.kedacom.device.core.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.device.core.constant.UmsMod;
import com.kedacom.device.core.convert.UmsSubDeviceConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.entity.SubDeviceInfoEntity;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.mapper.SubDeviceMapper;
import com.kedacom.device.core.utils.PinYinUtils;
import com.kedacom.device.ums.UmsClient;
import com.kedacom.device.ums.request.QueryDeviceRequest;
import com.kedacom.device.ums.response.QuerySubDeviceInfoResponse;
import com.kedacom.ums.responsedto.SubDeviceInfoResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.kedacom.device.core.constant.DeviceConstants.REQUEST3;

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

        if (responseVo.getResp().getErrorcode() != 0) {
            for (int i = 0; i < REQUEST3; i ++) {
                responseVo = umsClient.querydev(requestVo);
                if (responseVo.getResp().getErrorcode() == 0) {
                    break;
                }
                if (i == 2) {
                    log.error("手动同步设备第{}页异常，同步数据为{}条", curPage, pageSize);
                    return 0;
                }
            }
        }
        List<SubDeviceInfoResponseVo> responseVoList = responseVo.getDevinfo();
        log.info("当前第{}页查询到的数据条数是{}", curPage, responseVoList.size());
        if (CollUtil.isEmpty(responseVoList) && curPage != 1) {
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
                if (subDeviceInfoMapper.selectById(umsSubDeviceInfoEntity.getId()) != null) {
                    if (log.isDebugEnabled()){
                        log.info("已存在Id为:{}", umsSubDeviceInfoEntity.getId());
                    }
                    int i = subDeviceInfoMapper.updateById(umsSubDeviceInfoEntity);
                    if (i <= 0) {
                        log.error("设备id:{},更新失败", umsSubDeviceInfoEntity.getId());
                    }
                }else {
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






}
