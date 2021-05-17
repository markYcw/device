package com.kedacom.device.core.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.acl.network.ums.requestvo.QuerySubDeviceInfoRequestVo;
import com.kedacom.acl.network.ums.responsevo.QuerySubDeviceInfoResponseVo;
import com.kedacom.acl.network.ums.responsevo.SubDeviceInfoResponseVo;
import com.kedacom.device.core.constant.UmsMod;
import com.kedacom.device.core.convert.UmsSubDeviceConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.entity.SubDeviceInfoEntity;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.mapper.SubDeviceMapper;
import com.kedacom.device.ums.UmsClient;
import com.kedacom.device.ums.request.QueryDeviceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


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

        // log.info("查询第{}页数据", curPage);
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(umsDeviceId);
        String sessionId = deviceInfoEntity.getSessionId();
        QueryDeviceRequest requestVo = new QueryDeviceRequest();
        requestVo.setSsid(Integer.valueOf(sessionId));
        requestVo.setF_eq_parentid(umsDeviceId);
        requestVo.setQueryindex(curPage);
        requestVo.setQuerycount(pageSize);
        QuerySubDeviceInfoResponseVo responseVo = umsClient.querydev(requestVo);


//        if (!(UdmsCodeResult.SUCCESS.equals(baseResponseVo.getCode()) && Boolean.TRUE.equals(baseResponseVo.isRet()))) {
//            return curPage;
//        }


        List<SubDeviceInfoResponseVo> responseVoList = responseVo.getDevinfo();

        log.info("当前第{}页查询到的数据条数是{}", curPage, responseVoList.size());

        if (CollUtil.isEmpty(responseVoList)) {
            return Integer.MAX_VALUE;
        }


        List<SubDeviceInfoEntity> umsSubDeviceInfoEntityList = UmsSubDeviceConvert.INSTANCE.convertSubDeviceInfoEntityList(responseVoList);

        log.info("当前第{}页转化后得到的数据条数是{}", curPage, umsSubDeviceInfoEntityList.size());

        umsSubDeviceInfoEntityList.forEach(x -> x.setDeviceMod(UmsMod.NORMAL));

     //   this.saveOrUpdateBatch(umsSubDeviceInfoEntityList);

        for (SubDeviceInfoEntity umsSubDeviceInfoEntity : umsSubDeviceInfoEntityList) {

            if (umsSubDeviceInfoEntity != null && StrUtil.isNotBlank(umsSubDeviceInfoEntity.getId())) {

                if (subDeviceInfoMapper.selectById(umsSubDeviceInfoEntity.getId()) != null) {
                   // log.info("已存在Id为:{}", umsSubDeviceInfoEntity.getId());
                    subDeviceInfoMapper.updateById(umsSubDeviceInfoEntity);
                }else {
                    subDeviceInfoMapper.insert(umsSubDeviceInfoEntity);
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
