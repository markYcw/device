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
import com.kedacom.common.model.Result;
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
 * @Description 电源配置接口
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
    public Result<PowerDeviceEntity> addBwPower(BwPowerDeviceAddVo powerDeviceAddVo) {
        PowerDeviceEntity entity = dealInsertBw(powerDeviceAddVo);
        powerDeviceMapper.insert(entity);
        return Result.succeed(powerDeviceMapper.selectById(entity.getId()));
    }

    @Override
    public Result<PowerDeviceEntity> updateBwPower(BwPowerDeviceUpdateVo powerDeviceUpdateVo) {
        PowerDeviceEntity powerDeviceEntity = powerDeviceMapper.selectById(powerDeviceUpdateVo.getId());
        dealUpdateBw(powerDeviceEntity, powerDeviceUpdateVo);
        powerDeviceMapper.updateById(powerDeviceEntity);
        return Result.succeed(powerDeviceMapper.selectById(powerDeviceEntity.getId()));
    }

    /**
     * @param powerConfigAddVo
     * @Description 配置监听端口
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:59
     */
    @Override
    public Result<Integer> portAdd(PowerConfigAddVo powerConfigAddVo) {
        AssertBiz.OBJECT_NONE_NULL.notNull(powerConfigAddVo.getPort(), KmResultCodeEnum.ERROR_OF_PORT_REGEX);
        AssertBiz.OBJECT_IS_TRUE.isTrue(checkPortRegex(powerConfigAddVo.getPort()), KmResultCodeEnum.ERROR_OF_PORT_REGEX);

        PowerConfigEntity powerByPort = this.getPowerByPort(powerConfigAddVo.getPort());
        AssertBiz.OBJECT_NONE_NULL.isNull(powerByPort, KmResultCodeEnum.ERROR_OF_DATA_REPEAT);
        // 先删除全部配置
        powerConfigMapper.delete(new QueryWrapper<>());
        // 再新增配置
        PowerConfigEntity entity = PowerConfigEntity.builder().port(powerConfigAddVo.getPort()).build();
        powerConfigMapper.insert(entity);

        // 连接开启tcp连接端口
        startTcpOfPort(powerConfigAddVo.getPort());

        return Result.succeed(entity.getId());
    }

    /**
     * @param powerConfigUpdateVo
     * @Description 修改监听端口
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:59
     */
    @Override
    public Result<Integer> portUpdate(PowerConfigUpdateVo powerConfigUpdateVo) {
        AssertBiz.OBJECT_NONE_NULL.notNull(powerConfigUpdateVo.getPort(), KmResultCodeEnum.ERROR_OF_PORT_REGEX);
        AssertBiz.OBJECT_IS_TRUE.isTrue(checkPortRegex(powerConfigUpdateVo.getPort()), KmResultCodeEnum.ERROR_OF_PORT_REGEX);

        PowerConfigEntity powerConfigEntity = powerConfigMapper.selectById(powerConfigUpdateVo.getId());
        AssertBiz.OBJECT_NONE_NULL.notNull(powerConfigEntity, KmResultCodeEnum.ERROR_OF_DATA_NONE);
        powerConfigEntity.setPort(powerConfigUpdateVo.getPort());
        powerConfigMapper.updateById(powerConfigEntity);

        // 开启tcp连接端口
        startTcpOfPort(powerConfigUpdateVo.getPort());
        return Result.succeed(powerConfigUpdateVo.getId());
    }

    /**
     * @param powerPortVo
     * @Description 批量删除监听端口
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:58
     */
    @Override
    public Result<Boolean> portDelete(PowerPortVo powerPortVo) {
        powerConfigMapper.deleteBatchIds(powerPortVo.getIds());
        // 断开tcp连接
        ControlPower.getInstance().stopServer();
        this.changeBwantAllDeviceStatusDown();
        return Result.succeed(Boolean.TRUE);
    }

    /**
     * @param powerConfigListVo
     * @Description 查询所有监听端口
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:58
     */
    @Override
    public Result<List<PowerPortListVo>> portList(PowerConfigListVo powerConfigListVo) {
        LambdaQueryWrapper<PowerConfigEntity> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(powerConfigListVo.getId())) {
            wrapper.eq(PowerConfigEntity::getId, powerConfigListVo.getId());
        }
        List<PowerConfigEntity> powerConfigEntities = powerConfigMapper.selectList(wrapper);
        List<PowerPortListVo> powerPortListVos = powerPortConvert.convertToPowerPortListVo(powerConfigEntities);
        return Result.succeed(powerPortListVos);
    }

    /**
     * @param port
     * @Description 根据端口号查询配置
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:58
     */
    @Override
    public PowerConfigEntity getPowerByPort(int port) {
        LambdaQueryWrapper<PowerConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PowerConfigEntity::getPort, port);
        return powerConfigMapper.selectOne(wrapper);
    }


    /**
     * 处理BWANT_IPM_08类型的电源设备
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
        // 校验mac或者ip是否已存在
        LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PowerDeviceEntity::getMac, entity.getMac());
        List<PowerDeviceEntity> entities = powerDeviceMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(entities)) {
            log.error("添加北望电源设备已存在：{}", entities.get(0).getMac());
            throw new PowerServiceException(KmResultCodeEnum.ERROR_OF_DEVICE_ALREADY_INUSE);
        }
        return entity;
    }

    /**
     * 处理Rk100类型的电源设备
     */
    private PowerDeviceEntity dealInsertRk100(PowerDeviceAddVo powerDeviceAddVo) {
        PowerDeviceEntity entity = PowerDeviceEntity.builder()
                .ip(powerDeviceAddVo.getIp())
                .name(powerDeviceAddVo.getName())
                .type(powerDeviceAddVo.getType())
                .port(powerDeviceAddVo.getPort())
                .deviceSn(powerDeviceAddVo.getDeviceSn())
                .state(0) // 默认是离线
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
     * 修改BWANT_IPM_08类型的电源设备
     */
    private void dealUpdateBw(PowerDeviceEntity powerDeviceEntity, BwPowerDeviceUpdateVo vo) {
        if (StringUtils.isNotBlank(vo.getName())) {
            powerDeviceEntity.setName(vo.getName());
        }
        if (StringUtils.isNotBlank(vo.getIp())) {
            powerDeviceEntity.setIp(vo.getIp());
        }
        if (StrUtil.isNotBlank(vo.getMac())) {
            // 校验mac地址是否重复
            LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PowerDeviceEntity::getMac, powerDeviceEntity.getMac()).ne(PowerDeviceEntity::getId, vo.getId());
            List<PowerDeviceEntity> entities = powerDeviceMapper.selectList(wrapper);
            if (CollectionUtil.isNotEmpty(entities)) {
                log.error("修改北望电源设备已存在：{}", entities.get(0).getMac());
                throw new PowerServiceException(KmResultCodeEnum.ERROR_OF_DEVICE_ALREADY_INUSE);
            }
            powerDeviceEntity.setMac(vo.getMac());
        }
    }

    /**
     * 修改Rk100类型的电源设备
     */
    private void dealUpdateRK100(PowerDeviceEntity powerDeviceEntity, PowerDeviceUpdateVo powerDeviceUpdateVo) {
        //AssertBiz.OBJECT_IS_TRUE.isTrue(mapEntity.contains(powerDeviceEntity.getId()), KmResultCodeEnum.ERROR_OF_RK100_ALREADY_LOGIN_IN); // 已登录RK100设备的不允许修改

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
     * @Description 分页条件查询电源设备
     * @param:
     * @return:
     * @author:zlf
     * @date:2021/5/27 13:06
     */
    @Override
    public Result<PageRespVo<List<PowerDeviceListRspVo>>> deviceList(PowerDeviceListVo powerDeviceListVo) {
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
        return Result.succeed(PageRespVo.newInstance(powerDeviceListRspVos,
                powerDeviceEntityPage.getTotal(), powerDeviceEntityPage.getCurrent(), powerDeviceEntityPage.getSize()));
    }

    /**
     * @param powerDeviceDeleteVo
     * @Description 批量删除电源设备
     * @param:
     * @return:
     * @author:zlf
     * @date:2021/5/27 11:26
     */
    @Override
    public Result<Boolean> deviceDelete(PowerDeviceDeleteVo powerDeviceDeleteVo) {
        powerDeviceMapper.deleteBatchIds(powerDeviceDeleteVo.getIds());

        /*for (Integer id : powerDeviceDeleteVo.getIds()) {
            AssertBiz.OBJECT_IS_TRUE.isTrue(mapEntity.contains(id), KmResultCodeEnum.ERROR_OF_RK100_ALREADY_LOGIN_IN); // 已登录RK100设备的不允许删除
        }*/

        return Result.succeed(Boolean.TRUE);
    }

    /**
     * @Description 启动TCP连接
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:58
     */
    @Override
    public Result powerStart(int id) {
        PowerConfigEntity powerConfigEntity = powerConfigMapper.selectById(id);
        AssertBiz.OBJECT_NONE_NULL.notNull(powerConfigEntity, KmResultCodeEnum.ERROR_OF_DATA_NONE);

        startTcpOfPort(powerConfigEntity.getPort());
        return Result.succeed(KmResultCodeEnum.SUCCESS.getMessage());
    }

    /**
     * @Description 获取所有设备，填充下拉列表
     */
    @Override
    public Result<List<PowerDeviceVo>> getDeviceDatas() {
        List<PowerDeviceVo> powerDeviceVos = Lists.newArrayList();
        PowerDeviceVo powerDeviceVo = null;

        Map<String, Device> devices = ControlPower.getInstance().getDevices();
        log.info("获取所有设备集合：{}", JSON.toJSONString(devices));
        if (CollectionUtils.isEmpty(devices)) {
            return Result.succeed(powerDeviceVos);
        }
        Set<Map.Entry<String, Device>> entries = devices.entrySet();
        for (Map.Entry<String, Device> entry : entries) {
            String mac = entry.getKey();
            Device device = entry.getValue();
            // 校验mac或者ip是否已存在
            LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PowerDeviceEntity::getMac, device.getMacAddr());
            List<PowerDeviceEntity> entities = powerDeviceMapper.selectList(wrapper);
            if (CollectionUtil.isNotEmpty(entities)) {
                log.error("获取所有设备，填充下拉列表,电源设备已存在数据库：{}", entities.get(0).getMac());
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

        return Result.succeed(powerDeviceVos);
    }

    /**
     * @Description 获取所有设备
     * @param:
     * @return:
     * @author:zlf
     * @date:2021/5/27 13:50
     */
    @Override
    public Result<List<PowerDeviceListRspVo>> listDevices() {
        List<PowerDeviceEntity> powerDeviceEntities = powerDeviceMapper.selectList(new QueryWrapper<>());
        List<PowerDeviceListRspVo> powerDeviceListRspVos = powerPortConvert.convertToPowerDeviceListRspVo(powerDeviceEntities);
        return Result.succeed(powerDeviceListRspVos);
    }

    @Override
    public Result<PowerDeviceListRspVo> getDeviceById(int id) {
        PowerDeviceEntity entity = powerDeviceMapper.selectById(id);
        AssertBiz.OBJECT_NONE_NULL.notNull(entity, KmResultCodeEnum.ERROR_OF_DATA_NONE);
        PowerDeviceListRspVo powerDeviceListRspVo = powerPortConvert.convertToPowerDeviceListRspVo(entity);
        return Result.succeed(powerDeviceListRspVo);
    }

    /**
     * 局域网搜索，未添加记录排列在前面
     */
    @Override
    public List<LanDevice> searchDevices() throws Exception {
        Set<Device> devices = configPower.searchDevices();
        log.info("局域网搜索：{}",devices);
        Set<LanDevice> lanDevices = convert.convertDevices(devices);
        for (LanDevice lanDevice : lanDevices) {
            LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PowerDeviceEntity::getMac, lanDevice.getMacAddr());
            List<PowerDeviceEntity> entities = powerDeviceMapper.selectList(wrapper);
            lanDevice.setIsAdd(CollectionUtil.isNotEmpty(entities) ? 1 : 0);
        }
        List<LanDevice> lanDeviceList = lanDevices.stream()
                .sorted(Comparator.comparing(LanDevice::getIsAdd).reversed())
                .collect(Collectors.toList());
        return lanDeviceList;
    }

    @Override
    public Result<PowerLanConfigVO> getPowerConfigByMac(String macAddr) {
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
            log.error("根据设备Mac地址获取电源的详细配置，异常:{}", e.getMessage());
            return Result.failed("根据设备Mac地址获取电源的详细配置，异常");
        }
        return Result.succeed(vo);
    }

    @Override
    public Result updatePowerConfigByMac(UpdatePowerLanConfigDTO dto) {
        if (!isValidPort(dto.getDesPort())) {
            return Result.failed("服务器端口格式错误");
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
            return Result.succeed();
        }
        return Result.failed("修改电源配置失败");
    }

    /**
     * @param powerDeviceMessageReqVo
     * @Description 获取设备详细信息
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:58
     */
    @Override
    public Result<PowerDeviceMessageVo> deviceMessage(PowerDeviceMessageReqVo powerDeviceMessageReqVo) {
        PowerDeviceEntity powerDeviceEntity = powerDeviceMapper.selectById(powerDeviceMessageReqVo.getId());
        AssertBiz.OBJECT_NONE_NULL.notNull(powerDeviceEntity, KmResultCodeEnum.ERROR_OF_DATA_NONE);

        int voltage = ControlPower.getInstance().getVoltage(powerDeviceEntity.getMac());
        int temperature = ControlPower.getInstance().getTemperature(powerDeviceEntity.getMac());
        Map<Integer, Float> electricity = ControlPower.getInstance().getElectricity(powerDeviceEntity.getMac());
        log.info("获取设备详细信息：voltage: {}, temperature: {}, electricity: {}", voltage, temperature, JSON.toJSONString(electricity));
        List<PowerDeviceElectricityVo> electricityVos = null;
        if (electricity != null) {
            electricityVos = electricity.entrySet().stream().map(a -> {
                return PowerDeviceElectricityVo.builder().channel(a.getKey()).electricity(a.getValue()).build();
            }).collect(Collectors.toList());
        }


        return Result.succeed(PowerDeviceMessageVo.builder()
                .voltage(voltage)
                .temperature(temperature)
                .electricities(electricityVos)
                .build());
    }

    /**
     * @param powerDeviceMessageReqVo
     * @Description 获取设备下通道状态
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:58
     */
    @Override
    public Result<List<PowerChannelStateVo>> deviceChannelState(PowerDeviceMessageReqVo powerDeviceMessageReqVo) {
        PowerDeviceEntity powerDeviceEntity = powerDeviceMapper.selectById(powerDeviceMessageReqVo.getId());
        AssertBiz.OBJECT_NONE_NULL.notNull(powerDeviceEntity, KmResultCodeEnum.ERROR_OF_DATA_NONE);

        Map<Integer, Integer> channelState = ControlPower.getInstance().getChannelState(powerDeviceEntity.getMac());
        log.info("获取设备下通道状态：{}", JSON.toJSONString(channelState));
        if (CollectionUtils.isEmpty(channelState)) {
            return Result.succeed(Lists.newArrayList());
        }
        List<PowerChannelStateVo> powerChannelStateVos = channelState.entrySet().stream().map(a -> {
            return PowerChannelStateVo.builder().channel(a.getKey()).state(a.getValue()).build();
        }).collect(Collectors.toList());
        return Result.succeed(powerChannelStateVos);
    }

    @Override
    public Result<Boolean> deviceTurn(PowerDeviceTurnVO vo) {
        PowerDeviceEntity powerDeviceEntity = powerDeviceMapper.selectById(vo.getId());
        AssertBiz.OBJECT_NONE_NULL.notNull(powerDeviceEntity, KmResultCodeEnum.ERROR_OF_DATA_NONE);
        String mac = powerDeviceEntity.getMac();
        if (ObjectUtil.isNull(vo.getFlag())) {
            log.info("通道对应开关状态为空，操作失败！");
            return Result.failed(KmResultCodeEnum.ERROR.getMessage());
        }
        boolean flag = Objects.equals(1, vo.getFlag());
        log.info("单个通道开关请求参数：mac: {}, flag: {}", mac, vo.getFlag());
        return Result.succeed(ControlPower.getInstance().turn(mac, vo.getChannel(), flag));
    }

    /**
     * @param powerDeviceTurnsVo
     * @Description 多个通道开关
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:57
     */
    @Override
    public Result<Boolean> deviceTurns(PowerDeviceTurnsVo powerDeviceTurnsVo) {
        PowerDeviceEntity powerDeviceEntity = powerDeviceMapper.selectById(powerDeviceTurnsVo.getId());
        AssertBiz.OBJECT_NONE_NULL.notNull(powerDeviceEntity, KmResultCodeEnum.ERROR_OF_DATA_NONE);
        String mac = powerDeviceEntity.getMac();
        Map<Integer, Integer> channelState = ControlPower.getInstance().getChannelState(powerDeviceEntity.getMac());

        List<PowerDeviceChannelTurnsVo> channels = powerDeviceTurnsVo.getChannels();
        for (PowerDeviceChannelTurnsVo channel : channels) {
            channelState.put(channel.getChannel(), channel.getFlag());
        }
        if (CollectionUtils.isEmpty(channels)) {
            log.info("通道对应开关状态为空，操作失败！");
            return Result.failed(KmResultCodeEnum.ERROR.getMessage());
        }
        Map<Integer, Integer> stateMap = channels.stream()
                .collect(Collectors.toMap(PowerDeviceChannelTurnsVo::getChannel, PowerDeviceChannelTurnsVo::getFlag, (a1, a2) -> a1));
        log.info("多个通道开关请求参数：mac: {}, stateMap: {}", mac, JSON.toJSONString(stateMap));
        return Result.succeed(ControlPower.getInstance().turnMany(mac, channelState));
    }

    /**
     * @Description 停止监听端口
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:57
     */
    @Override
    public Result<Boolean> powerStop() {
        ControlPower.getInstance().stopServer();
        this.changeBwantAllDeviceStatusDown();
        return Result.succeed(KmResultCodeEnum.SUCCESS.getMessage());
    }

    /**
     * @param macAddr
     * @param state
     * @Description 修改电源设备状态
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
                log.info("修改电源设备状态，发布消息：{}", JSON.toJSONString(vo));
                redisTemplate.convertAndSend("powerState", JSON.toJSONString(vo));
            }
            return true;
        }
        return false;
    }

    /**
     * @Description 项目启动时，读取数据库配置，启动tcp连接
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
        log.info("项目启动，开启TcpServer，port：{}", powerConfigEntities.stream().map(PowerConfigEntity::getPort).collect(Collectors.toList()));
        powerConfigEntities.stream().forEach(power -> {
            startTcpOfPort(power.getPort());
        });
    }

    /**
     * 所有BWANT_IPM_08设备状态置为离线
     */
    @Override
    public void changeBwantAllDeviceStatusDown() {
        LambdaQueryWrapper<PowerDeviceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PowerDeviceEntity::getType, PowerTypeEnum.BWANT_IPM_08.getType());
        List<PowerDeviceEntity> powerDeviceEntities = powerDeviceMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(powerDeviceEntities)) {
            return;
        }
        log.info("TCP服务关闭，所有电源设备状态置为离线，name：{}", powerDeviceEntities.stream().map(PowerDeviceEntity::getName).collect(Collectors.toList()));
        List<Integer> ids = powerDeviceEntities.stream().map(PowerDeviceEntity::getId).collect(Collectors.toList());
        powerDeviceMapper.updateStatus(ids, 0);
        PowerStateVO vo = PowerStateVO.builder()
                .ids(ids)
                .state(0)
                .build();
        log.info("TCP服务关闭，所有电源设备状态置为离线，发布消息：{}", JSON.toJSONString(vo));
        redisTemplate.convertAndSend("powerState", JSON.toJSONString(vo));
    }

    /**
     * 获取电源支持的类型
     */
    @Override
    public Result<List<PowerDeviceTypeResponseVo>> getDevType() {
        List<PowerTypeEntity> powerTypeEntities = powerTypeMapper.selectList(new QueryWrapper<>());
        List<PowerDeviceTypeResponseVo> responseVos = powerTypeEntities.stream().map(p -> {
            return PowerDeviceTypeResponseVo.builder().devType(p.getDevType()).id(p.getId()).build();
        }).collect(Collectors.toList());
        return Result.succeed(responseVos);
    }


    /**
     * 开启tcp连接端口
     */
    private void startTcpOfPort(int port) {
        try {
            log.info("电源配置启动TCP端口号为：{}", port);
            ControlPower.getInstance().stopServer();
            this.changeBwantAllDeviceStatusDown();
            ControlPower.getInstance().startServer(port, new ControlPowerStatusCallback());
        } catch (Exception e) {
            log.error("开启tcp连接端口失败，case：" + e, e);
        }
    }

    /**
     * 校验端口号格式
     */
    private boolean checkPortRegex(int port) {
        String p = "^([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(String.valueOf(port));
        return matcher.matches();
    }

    public static void main(String[] args) {
        boolean validIpAddress = isValidMacAddr("84-c2e4-24-30-88");
        System.out.println("validIpAddress = " + validIpAddress);
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
     * 用来标识rabbitmq的连接数，key为ip+端口号，value为重入次数
     */
    private static ConcurrentHashMap<Integer, PowerDeviceEntity> mapEntity = new ConcurrentHashMap<Integer, PowerDeviceEntity>(); // 是否已登录
    private static ConcurrentHashMap<String, AtomicInteger> mapStatus = new ConcurrentHashMap<String, AtomicInteger>();


    /**
     * 初始化容量14，当10个RK100设备以内的情况下，只扩容一次
     */
    private HashMap<Integer, Timer> statusMap = new HashMap<Integer, Timer>(14);


    /**
     * 查询该Rabbitmq连接数，返回是否需要断开连接
     */
    private boolean isDeConnect(int id) {
        PowerDeviceEntity entity = powerDeviceMapper.selectById(id);
        AssertBiz.OBJECT_NONE_NULL.notNull(entity, KmResultCodeEnum.ERROR_OF_NONE_FOUND_DEVICE);
        /**
         把定时任务取消掉
         */
        if (statusMap.containsKey(id)) {
            Timer timer = statusMap.get(id);
            if (ObjectUtils.isNotNull(timer)) {
                timer.cancel();
            }
            statusMap.remove(id);
        }
        // 修改状态为离线
        powerDeviceMapper.updateStatus(Lists.newArrayList(id), 0);
        boolean flag = false;
        // 查询重入次数
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
