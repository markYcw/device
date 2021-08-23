package com.kedacom.device.controller;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.UmsMcuService;
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

    /**
     * 会议平台信息分页查询
     */
    @PostMapping("/pageQuery")
    @ApiOperation(value = "会议平台信息分页查询")
    public BaseResult<BasePage<UmsMcuEntity>> pageQuery(@RequestBody McuPageQueryDTO queryDTO) {
        BaseResult<BasePage<UmsMcuEntity>> basepPage = umsMcuService.pageQuery(queryDTO);

        return basepPage;
    }

    /**
     * 信息
     */
    @PostMapping("/info/{id}")
    @ApiOperation(value = "根据id获取会议平台信息")
    public BaseResult<UmsMcuEntity> info(@PathVariable("id") Long id) {
        UmsMcuEntity entity = umsMcuService.getById(id);

        return BaseResult.succeed(entity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增会议平台信息")
    public BaseResult<UmsMcuEntity> save(@Valid @RequestBody UmsMcuEntity entity, BindingResult br) {
        ValidUtils.paramValid(br);
        umsMcuService.save(entity);

        return BaseResult.succeed(entity);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改会议平台信息")
    public BaseResult update(@RequestBody UmsMcuEntity umsMeetingPlatform) {
        umsMcuService.updateById(umsMeetingPlatform);

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
     * 删除
     */
    @PostMapping("/mcuNotify")
    @ApiOperation(value = "会议平台通知")
    public void mcuNotify(@RequestBody String notify) {
        log.info("会议平台通知:{}", notify);

        umsMcuService.mcuNotify(notify);
    }


}
