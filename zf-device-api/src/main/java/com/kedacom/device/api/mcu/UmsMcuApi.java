package com.kedacom.device.api.mcu;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.mcu.fallback.UmsMcuApiFallbackFactory;
import com.kedacom.mp.mcu.entity.UmsMcuEntity;
import com.kedacom.mp.mcu.pojo.McuPageQueryDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/23 10:04
 * @description 会议平台 CRUD api
 */
@FeignClient(name = "device-server", contextId = "umcMcuApi", path = "/api-device/ums/umsMcu", fallbackFactory = UmsMcuApiFallbackFactory.class)
public interface UmsMcuApi {

    /**
     * 会议平台信息分页查询
     */
    @PostMapping("/pageQuery")
    @ApiOperation(value = "会议平台信息分页查询")
    BaseResult<BasePage<UmsMcuEntity>> pageQuery(@RequestBody McuPageQueryDTO queryDTO);

    @PostMapping("/all")
    @ApiOperation(value = "查询所有MCU")
    BaseResult<List<UmsMcuEntity>> all();

    /**
     * 信息
     */
    @PostMapping("/info/{id}")
    @ApiOperation(value = "根据id获取会议平台信息")
    public BaseResult<UmsMcuEntity> info(@PathVariable("id") Long id);

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增会议平台信息")
    BaseResult<UmsMcuEntity> save(@RequestBody UmsMcuEntity entity);

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改会议平台信息")
    BaseResult update(@RequestBody UmsMcuEntity entity);

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除会议平台信息")
    BaseResult delete(@RequestBody Long[] ids);

}
