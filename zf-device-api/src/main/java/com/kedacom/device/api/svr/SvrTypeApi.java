package com.kedacom.device.api.svr;


import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.svr.fallback.SvrApiFallbackFactory;
import com.kedacom.device.api.svr.fallback.SvrTypeApiFallbackFactory;
import com.kedacom.svr.entity.SvrTypeEntity;
import com.kedacom.svr.pojo.SvrTypePageQueryDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


/**
 * svr相关操作
 * @author ycw
 * @date 2021/09/07 14:55
 */
@FeignClient(name = "device-server", contextId = "svrTypeApi", path = "/api-device/ums/svrType",fallbackFactory = SvrTypeApiFallbackFactory.class)
public interface SvrTypeApi {

    @PostMapping("/pageQuery")
    @ApiOperation(value = "svr设备类型分页查询")
    public BaseResult<BasePage<SvrTypeEntity>> pageQuery(@RequestBody SvrTypePageQueryDTO queryDTO);

    @PostMapping("/all")
    @ApiOperation(value = "查询所有SVR设备类型")
    public BaseResult<List<SvrTypeEntity>> all();

    @PostMapping("/info")
    @ApiOperation(value = "根据id获取svr设备类型信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<SvrTypeEntity> info(@RequestParam Integer dbId);


    @PostMapping("/save")
    @ApiOperation(value = "新增svr设备类型")
    public BaseResult<SvrTypeEntity> save(@RequestBody SvrTypeEntity entity);

    @PostMapping("/update")
    @ApiOperation(value = "修改svr设备类型")
    public BaseResult<SvrTypeEntity> update(@RequestBody SvrTypeEntity entity);

    @PostMapping("/delete")
    @ApiOperation(value = "删除svr设备类型")
    public BaseResult delete(@RequestBody Long[] ids);



}
