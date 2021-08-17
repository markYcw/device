package com.kedacom.device.controller;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.UmsMeetingPlatformService;
import com.kedacom.mp.mcu.entity.UmsMeetingPlatformEntity;
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
@RequestMapping("ums/mp")
@Api(value = "会议平台信息", tags = "会议平台信息")
public class UmsMeetingPlatformController {

    @Autowired
    private UmsMeetingPlatformService platformService;

    /**
     * 会议平台信息分页查询
     */
    @PostMapping("/pageQuery")
    @ApiOperation(value = "会议平台信息分页查询")
    public BaseResult pageQuery(@RequestBody McuPageQueryDTO queryDTO) {
        BaseResult<BasePage<UmsMeetingPlatformEntity>> basepPage = platformService.pageQuery(queryDTO);

        return basepPage;
    }

    /**
     * 信息
     */
    @PostMapping("/info/{id}")
    @ApiOperation(value = "根据id获取会议平台信息")
    public BaseResult info(@PathVariable("id") Long id) {
        UmsMeetingPlatformEntity umsMeetingPlatform = platformService.getById(id);

        return BaseResult.succeed(umsMeetingPlatform);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增会议平台信息")
    public BaseResult save(@Valid @RequestBody UmsMeetingPlatformEntity umsMeetingPlatform, BindingResult br) {
        ValidUtils.paramValid(br);
        platformService.save(umsMeetingPlatform);

        return BaseResult.succeed("新增成功");
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改会议平台信息")
    public BaseResult update(@RequestBody UmsMeetingPlatformEntity umsMeetingPlatform) {
        platformService.updateById(umsMeetingPlatform);

        return BaseResult.succeed("修改成功");
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除会议平台信息")
    public BaseResult delete(@RequestBody Long[] ids) {
        platformService.removeByIds(Arrays.asList(ids));

        return BaseResult.succeed("删除成功");
    }

    /**
     * 删除
     */
    @PostMapping("/notify")
    @ApiOperation(value = "会议平台通知")
    public void notify(@RequestBody String notify) {


        log.info("会议平台通知:{}", notify);
    }


}
