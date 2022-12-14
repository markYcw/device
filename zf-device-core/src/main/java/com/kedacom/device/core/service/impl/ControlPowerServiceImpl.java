package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.kedacom.BaseResult;
import com.kedacom.device.core.convert.ControlPowerConvert;
import com.kedacom.device.core.convert.PowerPortConvert;
import com.kedacom.device.core.enums.AssertBiz;
import com.kedacom.device.core.enums.PowerTypeEnum;
import com.kedacom.device.core.exception.PowerServiceException;
import com.kedacom.device.core.mapper.PowerConfigMapper;
import com.kedacom.device.core.mapper.PowerDeviceMapper;
import com.kedacom.device.core.mapper.PowerTypeMapper;
import com.kedacom.device.core.power.ConfigPower;
import com.kedacom.device.core.service.ControlPowerService;
import com.kedacom.device.core.task.ControlPowerStatusCallback;
import com.kedacom.power.ControlPower;
import com.kedacom.power.dto.UpdatePowerLanConfigDTO;
import com.kedacom.power.entity.*;
import com.kedacom.power.model.KmResultCodeEnum;
import com.kedacom.power.model.PageRespVo;
import com.kedacom.power.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ClassName ControlPowerServiceImpl
 * @Description ??????????????????
 * @Author zlf
 * @Date 2021/5/25 13:56
 */
@Service
@Slf4j
public class ControlPowerServiceImpl implements ControlPowerService {

    @Autowired
    private PowerConfigMapper powerConfigMapper;

    @Autowired
    private PowerDeviceMapper powerDeviceMapper;

    @Autowired
    private PowerPortConvert powerPortConvert;

    @Autowired
    private PowerTypeMapper powerTypeMapper;

    @Autowired
    private ConfigPower configPower;

    @Autowired
    private ControlPowerConvert convert;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public BaseResult<PowerDeviceEntity> addBwPower(BwPowerDeviceAddVo powerDeviceAddVo) {
        PowerDeviceEntity entity = dealInsertBw(powerDeviceAddVo);
        powerDeviceMapper.insert(entity);
        return BaseResult.succeed(powerDeviceMapper.selectById(entity.getId()));
    }

    @Override
    public BaseResult<PowerDeviceEntity> updateBwPower(BwPowerDeviceUpdateVo powerDeviceUpdateVo) {
        PowerDeviceEntity powerDeviceEntity = powerDeviceMapper.selectById(powerDeviceUpdateVo.getId());
        dealUpdateBw(powerDeviceEntity, powerDeviceUpdateVo);
        powerDeviceMapper.updateById(powerDeviceEntity);
        return BaseResult.succeed(powerDeviceMapper.selectById(powerDeviceEntity.getId()));
    }

    /**
     * @param powerConfigAddVo
     * @Description ??????????????????
     * @param:
     * @return:
     * @author:?????????
     * @date:2021/5/25 13:59
     */
    @Override
    public BaseResult<Integer> portAdd(PowerConfigAddVo powerConfigAddVo) {
        AssertBiz.OBJECT_NONE_NULL.notNull(powerConfigAddVo.getPort(), KmResultCodeEnum.ERROR_OF_PORT_REGEX);
        AssertBiz.OBJECT_IS_TRUE.isTrue(checkPortRegex(powerConfigAddVo.getPort()), KmResultCodeEnum.ERROR_OF_PORT_REGEX);

        PowerConfigEntity powerByPort = this.getPowerByPort(powerConfigAddVo.getPort());
        AssertBiz.OBJECT_NONE_NULL.isNull(powerByPort, KmResultCodeEnum.ERROR_OF_DATA_REPEAT);
        // ?????????????????????
        powerConfigMapper.delete(new QueryWrapper<>());
        // ???????????????
        PowerConfigEntity entity = PowerConfigEntity.builder().port(powerConfigAddVo.getPort()).build();
        powerConfigMapper.insert(entity);

        // ????????????tcp????????????
        startTcpOfPort(powerConfigAddVo.getPort());

        return BaseResult.succeed(entity.getId());
    }

    /**
     * @param powerConfigUpdateVo
     * @Description ??????????????????
     * @param:
     * @return:
     * @author:?????????
     * @date:2021/5/25 13:59
     */
    @Override
    public BaseResult<Integer> portUpdate(PowerConfigUpdateVo powerConfigUpdateVo) {
        AssertBiz.OBJECT_NONE_NULL.notNull(powerConfigUpdateVo.getPort(), KmResultCodeEnum.ERROR_OF_PORT_REGEX);
        AssertBiz.OBJECT_IS_TRUE.isTrue(checkPortRegex(powerConfigUpdateVo.getPort()), KmResultCodeEnum.ERROR_OF_PORT_REGEX);

        PowerConfigEntity powerConfigEntity = powerConfigMapper.selectById(powerConfigUpdateVo.getId());
        AssertBiz.OBJECT_NONE_NULL.notNull(powerConfigEntity, KmResultCodeEnum.ERROR_OF_DATA_NONE);
        powerConfigEntity.setPort(powerConfigUpdateVo.getPort());
        powerConfigMapper.updateById(powerConfigEntity);

        // ??????tcp????????????
        startTcpOfPort(powerConfigUpdateVo.getPort());
        return BaseResult.succeed(powerConfigUpdateVo.getId());
    }

    /**
     * @param powerPortVo
     * @Description ????????????????????????
     * @param:
     * @return:
     * @author:?????????
     * @date:2021/5/25 13:58
     */
    @Override
    public BaseResult<Boolean> portDelete(PowerPortVo powerPortVo) {
        powerConfigMapper.deleteBatchIds(powerPortVo.getIds());
        // ??????tcp??????
        ControlPower.getInstance().stopServer();
        this.changeBwantAllDeviceStatusDown();
        return BaseResult.succeed(Boolean.TRUE);
    }

    /**
     * @param powerConfigListVo
     * @Description ????????????????????????
     * @param:
     * @return:
     * @author:?????????
     * @date:2021/5/25 13:58
     */
    @Override
    public BaseResult<List<PowerPortListVo>> portList(PowerConfigListVo powerConfigListVo) {
        LambdaQueryWrapper<PowerConfigEntity> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(powerConfigListVo.getId())) {
            wrapper.eq(PowerConfigEntity::getId, powerConfigListVo.getId());
        }
        List<PowerConfigEntity> powerConfigEntities = powerConfigMapper.selectList(wrapper);
        List<PowerPortListVo> powerPortListVos = powerPortConvert.convertToPowerPortListVo(powerConfigEntities);
        return BaseResult.succeed(powerPortListVos);
    }

    /**
     * @param port
     * @Description ???????????????????????????
     * @param:
     * @return:
     * @author:?????????
     * @date:2021/5/25 13:58
     */
    @Override
    public PowerConfigEntity getPowerByPort(int port) {
        LambdaQueryWrapper<PowerConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PowerConfigEntity::getPort, port);
        return powerConfigMapper.selectOne(wrapper);
    }


    /**
     * ??????BWANT_IPM_08?????????????????????
     */
    private PowerDeviceEntity dealInsertBw(BwPowerDeviceAddVo powerDeviceAddVo) {
        PowerDeviceEntity entity = PowerDeviceEntity.builder()
                .ip(powerDeviceAddVo.getIp())
                .name(powerDeviceAddVo.getName())
                .type(2)
                .mac(powerDeviceAddVo.getMac())
                .channels(powerDeviceAddVo.getChannels())
                .build();
        AssertBiz.OBJECT_NONE_NULL.notNull(entity.getMac(), KmResultCodeEnum.ERROR_OF_DEVICE_MAC_NOT_FOUND);
        // ??????mac??????ip???????????????
        LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PowerDeviceEntity::getMac, entity.getMac());
        List<PowerDeviceEntity> entities = powerDeviceMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(entities)) {
            log.error("????????????????????????????????????{}", entities.get(0).getMac());
            throw new PowerServiceException(KmResultCodeEnum.ERROR_OF_DEVICE_ALREADY_INUSE);
        }
        // ????????????????????????????????????
        if (entity.getChannels() != 4 && entity.getChannels() != 8) {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                log.error("??????????????????????????????????????????error???" + e);
            }
            Map<String, Device> devices = ControlPower.getInstance().getDevices();
            Device device = devices.get(entity.getMac());
            if (ObjectUtil.isNotNull(device)) {
                entity.setChannels(device.getChannels());
            }
        }
        return entity;
    }

    /**
     * ??????Rk100?????????????????????
     */
    private PowerDeviceEntity dealInsertRk100(PowerDeviceAddVo powerDeviceAddVo) {
        PowerDeviceEntity entity = PowerDeviceEntity.builder()
                .ip(powerDeviceAddVo.getIp())
                .name(powerDeviceAddVo.getName())
                .type(powerDeviceAddVo.getType())
                .port(powerDeviceAddVo.getPort())
                .deviceSn(powerDeviceAddVo.getDeviceSn())
                // ???????????????
                .state(0)
                .build();
        LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PowerDeviceEntity::getType, PowerTypeEnum.RK100.getType());
        List<PowerDeviceEntity> powerDeviceEntities = powerDeviceMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(powerDeviceEntities)) {
            for (PowerDeviceEntity powerDeviceEntity : powerDeviceEntities) {
                if (powerDeviceAddVo.getIp().equals(powerDeviceEntity.getIp()) && powerDeviceAddVo.getDeviceSn().equals(powerDeviceEntity.getDeviceSn())) {
                    throw new PowerServiceException(KmResultCodeEnum.ERROR_OF_RK100_IP_AND_DEVICE_SN_SEAM);
                }
                if (powerDeviceAddVo.getName().equals(powerDeviceEntity.getName())) {
                    throw new PowerServiceException(KmResultCodeEnum.ERROR_OF_RK100_NAME_NOT_NULL);
                }
            }
        }
        return entity;
    }


    /**
     * ??????BWANT_IPM_08?????????????????????
     */
    private void dealUpdateBw(PowerDeviceEntity powerDeviceEntity, BwPowerDeviceUpdateVo vo) {
        if (StringUtils.isNotBlank(vo.getName())) {
            powerDeviceEntity.setName(vo.getName());
        }
        if (StringUtils.isNotBlank(vo.getIp())) {
            powerDeviceEntity.setIp(vo.getIp());
        }
        if (StrUtil.isNotBlank(vo.getMac())) {
            // ??????mac??????????????????
            LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PowerDeviceEntity::getMac, powerDeviceEntity.getMac()).ne(PowerDeviceEntity::getId, vo.getId());
            List<PowerDeviceEntity> entities = powerDeviceMapper.selectList(wrapper);
            if (CollectionUtil.isNotEmpty(entities)) {
                log.error("????????????????????????????????????{}", entities.get(0).getMac());
                throw new PowerServiceException(KmResultCodeEnum.ERROR_OF_DEVICE_ALREADY_INUSE);
            }
            powerDeviceEntity.setMac(vo.getMac());
        }
    }

    /**
     * ??????Rk100?????????????????????
     */
    private void dealUpdateRK100(PowerDeviceEntity powerDeviceEntity, PowerDeviceUpdateVo powerDeviceUpdateVo) {
        //AssertBiz.OBJECT_IS_TRUE.isTrue(mapEntity.contains(powerDeviceEntity.getId()), KmResultCodeEnum.ERROR_OF_RK100_ALREADY_LOGIN_IN); // ?????????RK100????????????????????????

        LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PowerDeviceEntity::getType, PowerTypeEnum.RK100.getType());
        List<PowerDeviceEntity> powerDeviceEntities = powerDeviceMapper.selectList(wrapper);
        if (StringUtils.isNotBlank(powerDeviceUpdateVo.getName())) {
            if (CollectionUtils.isNotEmpty(powerDeviceEntities)) {
                for (PowerDeviceEntity entity : powerDeviceEntities) {
                    if (powerDeviceUpdateVo.getName().equals(entity.getName()) && !powerDeviceUpdateVo.getId().equals(entity.getId())) {
                        throw new PowerServiceException(KmResultCodeEnum.ERROR_OF_RK100_NAME_NOT_NULL);
                    }
                }
            }
            powerDeviceEntity.setName(powerDeviceUpdateVo.getName());
        }
        if (ObjectUtils.isNotNull(powerDeviceUpdateVo.getPort())) {
            AssertBiz.OBJECT_IS_TRUE.isTrue(checkPortRegex(powerDeviceUpdateVo.getPort()), KmResultCodeEnum.ERROR_OF_PORT_REGEX);
            powerDeviceEntity.setPort(powerDeviceUpdateVo.getPort());
        }
        if (StringUtils.isNotBlank(powerDeviceUpdateVo.getIp())) {
            powerDeviceEntity.setIp(powerDeviceUpdateVo.getIp());
        }
        if (StringUtils.isNotBlank(powerDeviceUpdateVo.getDeviceSn())) {
            if (CollectionUtils.isNotEmpty(powerDeviceEntities)) {
                for (PowerDeviceEntity entity : powerDeviceEntities) {
                    if (powerDeviceUpdateVo.getDeviceSn().equals(entity.getDeviceSn())
                            && !powerDeviceEntity.getId().equals(entity.getId())
                            && StringUtils.isNotBlank(powerDeviceUpdateVo.getIp())
                            && powerDeviceUpdateVo.getIp().equals(entity.getIp())) {
                        throw new PowerServiceException(KmResultCodeEnum.ERROR_OF_RK100_DEVICE_SN_NOT_NULL);
                    }
                }
            }
            powerDeviceEntity.setDeviceSn(powerDeviceUpdateVo.getDeviceSn());
        }
        powerDeviceEntity.setState(0);
    }

    /**
     * @param powerDeviceListVo
     * @Description ??????????????????????????????
     * @param:
     * @return:
     * @author:zlf
     * @date:2021/5/27 13:06
     */
    @Override
    public BaseResult<PageRespVo<List<PowerDeviceListRspVo>>> deviceList(PowerDeviceListVo powerDeviceListVo) {
        Page<PowerDeviceEntity> page = new Page<>(powerDeviceListVo.getPageIndex(), powerDeviceListVo.getPageSize());
        LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(powerDeviceListVo.getIp())) {
            wrapper.like(PowerDeviceEntity::getIp, powerDeviceListVo.getIp());
        }
        if (StringUtils.isNotBlank(powerDeviceListVo.getName())) {
            wrapper.like(PowerDeviceEntity::getName, powerDeviceListVo.getName());
        }
        if (ObjectUtil.isNotNull(powerDeviceListVo.getState())) {
            wrapper.eq(PowerDeviceEntity::getState, powerDeviceListVo.getState());
        }
        Page<PowerDeviceEntity> powerDeviceEntityPage = powerDeviceMapper.selectPage(page, wrapper);
        List<PowerDeviceListRspVo> powerDeviceListRspVos = powerPortConvert.convertToPowerDeviceListRspVo(powerDeviceEntityPage.getRecords());
        Map<String, Device> devices = ControlPower.getInstance().getDevices();
        LambdaUpdateWrapper<PowerDeviceEntity> updateWrapper = new LambdaUpdateWrapper<>();
        for (PowerDeviceListRspVo vo : powerDeviceListRspVos) {
            if (vo.getChannels() == 0) {
                Device device = devices.get(vo.getMac());
                if (ObjectUtil.isNotNull(device)) {
                    updateWrapper.clear();
                    updateWrapper.eq(PowerDeviceEntity::getId, vo.getId())
                            .set(PowerDeviceEntity::getChannels, vo.getChannels());
                    powerDeviceMapper.update(null, updateWrapper);
                    vo.setChannels(device.getChannels());
                }
            }
        }
        return BaseResult.succeed(PageRespVo.newInstance(powerDeviceListRspVos,
                powerDeviceEntityPage.getTotal(), powerDeviceEntityPage.getCurrent(), powerDeviceEntityPage.getSize()));
    }

    /**
     * @param powerDeviceDeleteVo
     * @Description ????????????????????????
     * @param:
     * @return:
     * @author:zlf
     * @date:2021/5/27 11:26
     */
    @Override
    public BaseResult<Boolean> deviceDelete(PowerDeviceDeleteVo powerDeviceDeleteVo) {
        powerDeviceMapper.deleteBatchIds(powerDeviceDeleteVo.getIds());

        /*for (Integer id : powerDeviceDeleteVo.getIds()) {
            AssertBiz.OBJECT_IS_TRUE.isTrue(mapEntity.contains(id), KmResultCodeEnum.ERROR_OF_RK100_ALREADY_LOGIN_IN); // ?????????RK100????????????????????????
        }*/

        return BaseResult.succeed(Boolean.TRUE);
    }

    /**
     * @Description ??????TCP??????
     * @param:
     * @return:
     * @author:?????????
     * @date:2021/5/25 13:58
     */
    @Override
    public BaseResult powerStart(int id) {
        PowerConfigEntity powerConfigEntity = powerConfigMapper.selectById(id);
        AssertBiz.OBJECT_NONE_NULL.notNull(powerConfigEntity, KmResultCodeEnum.ERROR_OF_DATA_NONE);

        startTcpOfPort(powerConfigEntity.getPort());
        return BaseResult.succeed(KmResultCodeEnum.SUCCESS.getMessage());
    }

    /**
     * @Description ???????????????????????????????????????
     */
    @Override
    public BaseResult<List<PowerDeviceVo>> getDeviceDatas() {
        List<PowerDeviceVo> powerDeviceVos = Lists.newArrayList();
        PowerDeviceVo powerDeviceVo = null;

        Map<String, Device> devices = ControlPower.getInstance().getDevices();
        log.info("???????????????????????????{}", JSON.toJSONString(devices));
        if (CollectionUtils.isEmpty(devices)) {
            return BaseResult.succeed(powerDeviceVos);
        }
        Set<Map.Entry<String, Device>> entries = devices.entrySet();
        for (Map.Entry<String, Device> entry : entries) {
            String mac = entry.getKey();
            Device device = entry.getValue();
            // ??????mac??????ip???????????????
            LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PowerDeviceEntity::getMac, device.getMacAddr());
            List<PowerDeviceEntity> entities = powerDeviceMapper.selectList(wrapper);
            if (CollectionUtil.isNotEmpty(entities)) {
                log.error("???????????????????????????????????????,?????????????????????????????????{}", entities.get(0).getMac());
                continue;
            }
            powerDeviceVo = new PowerDeviceVo();
            powerDeviceVo.setDeviceName(device.getDeviceName());
            powerDeviceVo.setIpAddr(device.getIpAddr());
            powerDeviceVo.setMacAddr(device.getMacAddr());
            if (ObjectUtil.isNotNull(device.getChannels())) {
                powerDeviceVo.setChannels(device.getChannels());
            }
            powerDeviceVos.add(powerDeviceVo);
        }

        return BaseResult.succeed(powerDeviceVos);
    }

    /**
     * @Description ??????????????????
     * @param:
     * @return:
     * @author:zlf
     * @date:2021/5/27 13:50
     */
    @Override
    public BaseResult<List<PowerDeviceListRspVo>> listDevices() {
        List<PowerDeviceEntity> powerDeviceEntities = powerDeviceMapper.selectList(new QueryWrapper<>());
        List<PowerDeviceListRspVo> powerDeviceListRspVos = powerPortConvert.convertToPowerDeviceListRspVo(powerDeviceEntities);
        return BaseResult.succeed(powerDeviceListRspVos);
    }

    @Override
    public BaseResult<PowerDeviceListRspVo> getDeviceById(int id) {
        PowerDeviceEntity entity = powerDeviceMapper.selectById(id);
        AssertBiz.OBJECT_NONE_NULL.notNull(entity, KmResultCodeEnum.ERROR_OF_DATA_NONE);
        PowerDeviceListRspVo powerDeviceListRspVo = powerPortConvert.convertToPowerDeviceListRspVo(entity);
        return BaseResult.succeed(powerDeviceListRspVo);
    }

    /**
     * ????????????????????????????????????????????????
     */
    @Override
    public List<LanDevice> searchDevices() throws Exception {
        Set<Device> devices = configPower.searchDevices();
        log.info("??????????????????{}", devices);
        Set<LanDevice> lanDevices = convert.convertDevices(devices);
        for (LanDevice lanDevice : lanDevices) {
            LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PowerDeviceEntity::getMac, lanDevice.getMacAddr());
            List<PowerDeviceEntity> entities = powerDeviceMapper.selectList(wrapper);
            lanDevice.setIsAdd(CollectionUtil.isNotEmpty(entities) ? 1 : 0);
        }
        List<LanDevice> lanDeviceList = lanDevices.stream()
                .sorted(Comparator.comparing(LanDevice::getIsAdd))
                .collect(Collectors.toList());
        return lanDeviceList;
    }

    @Override
    public BaseResult<PowerLanConfigVO> getPowerConfigByMac(String macAddr) {
        DevicePortConfig devicePortConfig = null;
        PowerLanConfigVO vo = null;
        try {
            NetDeviceConfig config = configPower.getConfig(macAddr);
            vo = new PowerLanConfigVO();
            DeviceConfig deviceConfig = config.getDeviceConfig();
            vo.setName(deviceConfig.getModuleName().replaceAll("\\u0000", ""));
            vo.setIp(deviceConfig.getDevIp());
            List<DevicePortConfig> devicePortConfigs = config.getDevicePortConfigs();
            for (DevicePortConfig portConfig : devicePortConfigs) {
                if (Objects.equals("1", portConfig.getPortEn())) {
                    devicePortConfig = portConfig;
                    break;
                }
            }
            vo.setDesIp(devicePortConfig.getDesIp());
            vo.setDesPort(devicePortConfig.getDesPort());
            vo.setDevGatewayIp(deviceConfig.getDevGatewayIp());
            vo.setDevIpMask(deviceConfig.getDevIpMask());
        } catch (Exception e) {
            log.error("????????????Mac??????????????????????????????????????????:{}", e.getMessage());
            return BaseResult.failed("????????????Mac??????????????????????????????????????????");
        }
        return BaseResult.succeed(vo);
    }

    @Override
    public BaseResult<Boolean> updatePowerConfigByMac(UpdatePowerLanConfigDTO dto) {
        if (!isValidPort(dto.getDesPort())) {
            return BaseResult.failed("???????????????????????????");
        }
        Config build = Config.builder()
                .devIp(dto.getIp())
                .devIpMask(dto.getDevIpMask())
                .desIp(dto.getDesIp())
                .desPort(dto.getDesPort())
                .devGwIp(dto.getDevGatewayIp()).build();
        int config = configPower.config(build, dto.getMac());
        if (config == 0) {
            LambdaUpdateWrapper<PowerDeviceEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(PowerDeviceEntity::getMac, dto.getMac())
                    .set(PowerDeviceEntity::getIp, dto.getIp());
            powerDeviceMapper.update(null, wrapper);
            return BaseResult.succeed(true);
        }
        return BaseResult.failed("????????????????????????");
    }

    /**
     * @param powerDeviceMessageReqVo
     * @Description ????????????????????????
     * @param:
     * @return:
     * @author:?????????
     * @date:2021/5/25 13:58
     */
    @Override
    public BaseResult<PowerDeviceMessageVo> deviceMessage(PowerDeviceMessageReqVo powerDeviceMessageReqVo) {
        PowerDeviceEntity powerDeviceEntity = powerDeviceMapper.selectById(powerDeviceMessageReqVo.getId());
        AssertBiz.OBJECT_NONE_NULL.notNull(powerDeviceEntity, KmResultCodeEnum.ERROR_OF_DATA_NONE);

        int voltage = ControlPower.getInstance().getVoltage(powerDeviceEntity.getMac());
        int temperature = ControlPower.getInstance().getTemperature(powerDeviceEntity.getMac());
        Map<Integer, Float> electricity = ControlPower.getInstance().getElectricity(powerDeviceEntity.getMac());
        log.info("???????????????????????????voltage: {}, temperature: {}, electricity: {}", voltage, temperature, JSON.toJSONString(electricity));
        List<PowerDeviceElectricityVo> electricityVos = null;
        if (electricity != null) {
            electricityVos = electricity.entrySet().stream().map(a -> {
                return PowerDeviceElectricityVo.builder().channel(a.getKey()).electricity(a.getValue()).build();
            }).collect(Collectors.toList());
        }


        return BaseResult.succeed(PowerDeviceMessageVo.builder()
                .voltage(voltage)
                .temperature(temperature)
                .electricities(electricityVos)
                .build());
    }

    /**
     * @param powerDeviceMessageReqVo
     * @Description ???????????????????????????
     * @param:
     * @return:
     * @author:?????????
     * @date:2021/5/25 13:58
     */
    @Override
    public BaseResult<List<PowerChannelStateVo>> deviceChannelState(PowerDeviceMessageReqVo powerDeviceMessageReqVo) {
        PowerDeviceEntity powerDeviceEntity = powerDeviceMapper.selectById(powerDeviceMessageReqVo.getId());
        AssertBiz.OBJECT_NONE_NULL.notNull(powerDeviceEntity, KmResultCodeEnum.ERROR_OF_DATA_NONE);

        Map<Integer, Integer> channelState = ControlPower.getInstance().getChannelState(powerDeviceEntity.getMac());
        log.info("??????????????????????????????{}", JSON.toJSONString(channelState));
        if (CollectionUtils.isEmpty(channelState)) {
            return BaseResult.succeed(Lists.newArrayList());
        }
        List<PowerChannelStateVo> powerChannelStateVos = channelState.entrySet().stream().map(a -> {
            return PowerChannelStateVo.builder().channel(a.getKey()).state(a.getValue()).build();
        }).collect(Collectors.toList());
        return BaseResult.succeed(powerChannelStateVos);
    }

    @Override
    public BaseResult<Boolean> deviceTurn(PowerDeviceTurnVO vo) {
        PowerDeviceEntity powerDeviceEntity = powerDeviceMapper.selectById(vo.getId());
        AssertBiz.OBJECT_NONE_NULL.notNull(powerDeviceEntity, KmResultCodeEnum.ERROR_OF_DATA_NONE);
        String mac = powerDeviceEntity.getMac();
        if (ObjectUtil.isNull(vo.getFlag())) {
            log.info("????????????????????????????????????????????????");
            return BaseResult.failed(KmResultCodeEnum.ERROR.getMessage());
        }
        boolean flag = Objects.equals(1, vo.getFlag());
        log.info("?????????????????????????????????mac: {}, flag: {}", mac, vo.getFlag());
        return BaseResult.succeed(ControlPower.getInstance().turn(mac, vo.getChannel(), flag));
    }

    /**
     * @param powerDeviceTurnsVo
     * @Description ??????????????????
     * @param:
     * @return:
     * @author:?????????
     * @date:2021/5/25 13:57
     */
    @Override
    public BaseResult<Boolean> deviceTurns(PowerDeviceTurnsVo powerDeviceTurnsVo) {
        PowerDeviceEntity powerDeviceEntity = powerDeviceMapper.selectById(powerDeviceTurnsVo.getId());
        AssertBiz.OBJECT_NONE_NULL.notNull(powerDeviceEntity, KmResultCodeEnum.ERROR_OF_DATA_NONE);
        String mac = powerDeviceEntity.getMac();
        Map<Integer, Integer> channelState = ControlPower.getInstance().getChannelState(powerDeviceEntity.getMac());

        List<PowerDeviceChannelTurnsVo> channels = powerDeviceTurnsVo.getChannels();
        for (PowerDeviceChannelTurnsVo channel : channels) {
            channelState.put(channel.getChannel(), channel.getFlag());
        }
        if (CollectionUtils.isEmpty(channels)) {
            log.info("????????????????????????????????????????????????");
            return BaseResult.failed(KmResultCodeEnum.ERROR.getMessage());
        }
        Map<Integer, Integer> stateMap = channels.stream()
                .collect(Collectors.toMap(PowerDeviceChannelTurnsVo::getChannel, PowerDeviceChannelTurnsVo::getFlag, (a1, a2) -> a1));
        log.info("?????????????????????????????????mac: {}, stateMap: {}", mac, JSON.toJSONString(stateMap));
        return BaseResult.succeed(ControlPower.getInstance().turnMany(mac, channelState));
    }

    /**
     * @Description ??????????????????
     * @param:
     * @return:
     * @author:?????????
     * @date:2021/5/25 13:57
     */
    @Override
    public BaseResult<Boolean> powerStop() {
        ControlPower.getInstance().stopServer();
        this.changeBwantAllDeviceStatusDown();
        return BaseResult.succeed(KmResultCodeEnum.SUCCESS.getMessage());
    }

    /**
     * @param macAddr
     * @param state
     * @Description ????????????????????????
     * @param:
     * @return:
     * @author:zlf
     * @date:2021/5/27 14:13
     */
    @Override
    public boolean changePowerDeviceState(String macAddr, String ip, int state) {
        LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PowerDeviceEntity::getMac, macAddr);
        PowerDeviceEntity powerDeviceEntity = powerDeviceMapper.selectOne(wrapper);
        log.info("????????????????????????-mac???{}???ip:{}????????????{}?????????????????????{}", macAddr, ip, state, JSON.toJSONString(powerDeviceEntity));
        if (ObjectUtils.isNotEmpty(powerDeviceEntity)) {
            powerDeviceEntity.setState(state);
            if (powerDeviceEntity.getIp().equals(ip)) {
                powerDeviceMapper.updateById(powerDeviceEntity);
                List<Integer> ids = new ArrayList<>();
                ids.add(powerDeviceEntity.getId());
                PowerStateVO vo = PowerStateVO.builder()
                        .ids(ids)
                        .state(state)
                        .build();
                log.info("??????????????????????????????????????????{}", JSON.toJSONString(vo));
                redisTemplate.convertAndSend("powerState", JSON.toJSONString(vo));
            }
            return true;
        }
        return false;
    }

    /**
     * @Description ????????????????????????????????????????????????tcp??????
     * @param:
     * @return:
     * @author:zlf
     * @date:2021/5/28 9:23
     */
    @Override
    public void tcpOnServerStart() {
        List<PowerConfigEntity> powerConfigEntities = powerConfigMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isEmpty(powerConfigEntities)) {
            return;
        }
        log.info("?????????????????????TcpServer???port???{}", powerConfigEntities.stream().map(PowerConfigEntity::getPort).collect(Collectors.toList()));
        powerConfigEntities.stream().forEach(power -> {
            startTcpOfPort(power.getPort());
        });
    }

    /**
     * ??????BWANT_IPM_08????????????????????????
     */
    @Override
    public void changeBwantAllDeviceStatusDown() {
        LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PowerDeviceEntity::getType, PowerTypeEnum.BWANT_IPM_08.getType());
        List<PowerDeviceEntity> powerDeviceEntities = powerDeviceMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(powerDeviceEntities)) {
            return;
        }
        log.info("TCP??????????????????????????????????????????????????????name???{}", powerDeviceEntities.stream().map(PowerDeviceEntity::getName).collect(Collectors.toList()));
        List<Integer> ids = powerDeviceEntities.stream().map(PowerDeviceEntity::getId).collect(Collectors.toList());
        powerDeviceMapper.updateStatus(ids, 0);
        PowerStateVO vo = PowerStateVO.builder()
                .ids(ids)
                .state(0)
                .build();
        log.info("TCP?????????????????????????????????????????????????????????????????????{}", JSON.toJSONString(vo));
        redisTemplate.convertAndSend("powerState", JSON.toJSONString(vo));
    }

    /**
     * ???????????????????????????
     */
    @Override
    public BaseResult<List<PowerDeviceTypeResponseVo>> getDevType() {
        List<PowerTypeEntity> powerTypeEntities = powerTypeMapper.selectList(new QueryWrapper<>());
        List<PowerDeviceTypeResponseVo> responseVos = powerTypeEntities.stream().map(p -> {
            return PowerDeviceTypeResponseVo.builder().devType(p.getDevType()).id(p.getId()).build();
        }).collect(Collectors.toList());
        return BaseResult.succeed(responseVos);
    }


    /**
     * ??????tcp????????????
     */
    private void startTcpOfPort(int port) {
        try {
            log.info("??????????????????TCP???????????????{}", port);
            ControlPower.getInstance().stopServer();
            this.changeBwantAllDeviceStatusDown();
            ControlPower.getInstance().startServer(port, new ControlPowerStatusCallback());
        } catch (Exception e) {
            log.error("??????tcp?????????????????????case???" + e, e);
        }
    }

    /**
     * ?????????????????????
     */
    private boolean checkPortRegex(int port) {
        String p = "^([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(String.valueOf(port));
        return matcher.matches();
    }

    public static void main(String[] args) {
        boolean b = get(0);
        System.out.println("b = " + b);
    }

    public static boolean get(Integer a) {
        return a != 4 && a != 8;
    }

    public static boolean isValidIpAddress(String ipAddress) {
        if (StrUtil.isBlank(ipAddress)) {
            return false;
        }
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ipAddress.matches(regex);
    }

    public static boolean isValidPort(Integer port) {
        if (ObjectUtil.isNull(port)) {
            return false;
        }
        String regex = "([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{4}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])";
        return port.toString().matches(regex);
    }

    public static boolean isValidMacAddr(String macAddr) {
        if (StrUtil.isBlank(macAddr)) {
            return false;
        }
        String regex = "^[a-fA-F0-9]{2}(-[a-fA-F0-9]{2}){5}$";
        return macAddr.matches(regex);
    }

    /**
     * ????????????rabbitmq???????????????key???ip+????????????value???????????????
     */
    private static ConcurrentHashMap<Integer, PowerDeviceEntity> mapEntity = new ConcurrentHashMap<Integer, PowerDeviceEntity>(); // ???????????????
    private static ConcurrentHashMap<String, AtomicInteger> mapStatus = new ConcurrentHashMap<String, AtomicInteger>();


    /**
     * ???????????????14??????10???RK100??????????????????????????????????????????
     */
    private HashMap<Integer, Timer> statusMap = new HashMap<Integer, Timer>(14);


    /**
     * ?????????Rabbitmq??????????????????????????????????????????
     */
    private boolean isDeConnect(int id) {
        PowerDeviceEntity entity = powerDeviceMapper.selectById(id);
        AssertBiz.OBJECT_NONE_NULL.notNull(entity, KmResultCodeEnum.ERROR_OF_NONE_FOUND_DEVICE);
        /**
         ????????????????????????
         */
        if (statusMap.containsKey(id)) {
            Timer timer = statusMap.get(id);
            if (ObjectUtils.isNotNull(timer)) {
                timer.cancel();
            }
            statusMap.remove(id);
        }
        // ?????????????????????
        powerDeviceMapper.updateStatus(Lists.newArrayList(id), 0);
        boolean flag = false;
        // ??????????????????
        if (mapEntity.contains(id)) {
            AtomicInteger atomicInteger = mapStatus.get(entity.getIp() + entity.getPort());
            atomicInteger.decrementAndGet();
            if (0 >= atomicInteger.intValue()) {
                flag = true;
                mapStatus.remove(entity.getIp() + entity.getPort());
            }
        }
        mapEntity.remove(id);
        return flag;
    }


}
