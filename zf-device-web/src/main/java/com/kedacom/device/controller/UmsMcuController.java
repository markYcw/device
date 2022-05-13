package com.kedacom.device.controller;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.convert.McuConvert;
import com.kedacom.device.core.service.UmsMcuService;
import com.kedacom.mp.mcu.entity.McuEntity;
import com.kedacom.mp.mcu.entity.UmsMcuEntity;
import com.kedacom.mp.mcu.pojo.McuPageQueryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 会议平台
 *
 * @author hxj
 * @email hexijian@kedacom.com
 * @date 2021-08-12 10:19:30
 */
@Slf4j
@RestController
@RequestMapping("ums/umsMcu")
@Api(value = "会议平台信息", tags = "会议平台信息")
public class UmsMcuController {

    @Autowired
    private UmsMcuService umsMcuService;

    @Autowired
    private McuConvert convert;

    /**
     * 会议平台信息分页查询
     */
    @PostMapping("/pageQuery")
    @ApiOperation(value = "会议平台信息分页查询")
    public BaseResult<BasePage<UmsMcuEntity>> pageQuery(@RequestBody McuPageQueryDTO queryDTO) {
        BaseResult<BasePage<UmsMcuEntity>> basepPage = umsMcuService.pageQuery(queryDTO);

        return basepPage;
    }


    @PostMapping("/all")
    @ApiOperation(value = "查询所有MCU")
    public BaseResult<List<UmsMcuEntity>> all() {

        List<McuEntity> list = umsMcuService.list();
        List<UmsMcuEntity> collect = list.stream().map(a -> convert.convertToUmsMcuEntity(a)).collect(Collectors.toList());


        return BaseResult.succeed(collect);
    }

    /**
     * 信息
     */
    @PostMapping("/info/{id}")
    @ApiOperation(value = "根据id获取会议平台信息")
    public BaseResult<UmsMcuEntity> info(@PathVariable("id") Long id) {
        McuEntity entity = umsMcuService.getById(id);
        UmsMcuEntity umsMcuEntity = convert.convertToUmsMcuEntity(entity);

        return BaseResult.succeed(umsMcuEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增会议平台信息")
    public BaseResult<UmsMcuEntity> save(@Valid @RequestBody UmsMcuEntity entity, BindingResult br) {
        ValidUtils.paramValid(br);
        McuEntity mcuEntity = convert.convertToEntity(entity);
        umsMcuService.save(mcuEntity);

        return BaseResult.succeed(entity);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改会议平台信息")
    public BaseResult update(@RequestBody UmsMcuEntity entity) {
        McuEntity mcuEntity = convert.convertToEntity(entity);
        umsMcuService.updateById(mcuEntity);

        return BaseResult.succeed("修改成功");
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除会议平台信息")
    public BaseResult delete(@RequestBody Long[] ids) {
        umsMcuService.removeByIds(Arrays.asList(ids));

        return BaseResult.succeed("删除成功");
    }

    /**
     * 通知
     */
    @PostMapping("/mcuNotify")
    @ApiOperation(value = "会议平台通知")
    public void mcuNotify(@RequestBody String notify) {
        log.info("会议平台通知:{}", notify);

        umsMcuService.mcuNotify(notify);
    }

}
