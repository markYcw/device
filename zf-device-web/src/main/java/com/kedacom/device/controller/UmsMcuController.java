package com.kedacom.device.controller;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.UmsMcuService;
import com.kedacom.mp.mcu.pojo.McuPageQueryDTO;
import com.kedacom.mp.mcu.response.McuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    public BaseResult<BasePage<McuVo>> pageQuery(@RequestBody McuPageQueryDTO queryDTO) {
        BaseResult<BasePage<McuVo>> basepPage = umsMcuService.pageQuery(queryDTO);

        return basepPage;
    }

    /**
     * 信息
     */
    @PostMapping("/info")
    @ApiOperation(value = "根据id获取会议平台信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "MCU数据库ID")})
    public BaseResult<McuVo> info(@RequestParam Long id) {

         return umsMcuService.info(id);
    }

    /**
     * 保存
     */
    @PostMapping("/saveMcu")
    @ApiOperation(value = "新增会议平台信息")
    public BaseResult<McuVo> saveMcu(@Valid @RequestBody McuVo mcuVo, BindingResult br) {
        ValidUtils.paramValid(br);
        return umsMcuService.saveMcu(mcuVo);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改会议平台信息")
    public BaseResult update(@RequestBody McuVo vo) {


        return umsMcuService.updateByVo(vo);
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
