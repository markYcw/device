package com.kedacom.device.api.vs;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.vs.fallback.VrsFiveDefaultFallbackFactory;
import com.kedacom.vs.entity.VsEntity;
import com.kedacom.vs.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 录播服务器5.1
 */
@FeignClient(value = "device-server", path = "/api-device/ums/vs", contextId = "vrsFiveApi", fallbackFactory = VrsFiveDefaultFallbackFactory.class)
public interface VrsFiveApi {

    @ApiOperation("返回VRS2100/4100所有记录")
    @PostMapping("/vrsFiveList")
    public BaseResult<List<VrsVo>> vrsFiveList();

    @ApiOperation("分页查询vrs信息")
    @PostMapping("/vrsList")
    public BaseResult<BasePage<VsEntity>> terminalList(@RequestBody VrsQuery vrsQuery);

    @ApiOperation("保存VRS信息")
    @PostMapping("/saveVrs")
    public BaseResult<VrsVo> saveVrs(@RequestBody VrsVo vrsVo);

    @ApiOperation("删除VRS信息")
    @PostMapping("/deleteVrs")
    public BaseResult deleteVrs(@RequestBody List<Integer> ids);

    @ApiOperation("修改VRS信息")
    @PostMapping("/updateVrs")
    public BaseResult<VrsVo> updateVrs(@RequestBody VrsVo vrsVo);

    @ApiOperation("查询VRS信息")
    @PostMapping("/vtsInfo")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "数据库ID")})
    public BaseResult<VrsVo> vtsInfo(@RequestParam Integer id);

    @ApiOperation("分页查询HTTP录像 5.1版本才支持")
    @PostMapping("/vrsQueryHttpRec")
    public BaseResult<VrsRecInfoDecVo> vrsQueryHttpRec(@RequestBody QueryRecListVo queryRecListVo);

    @ApiOperation("查询录像")
    @PostMapping("/queryRec")
    public BaseResult<VrsRecInfoVo> queryRec(@RequestBody QueryRecVo vo);

    @ApiOperation("查询直播 5.1版本才支持")
    @PostMapping("/queryRec")
    public BaseResult<LiveInfoVo> queryLive(@RequestBody QueryLiveVo vo);

    @ApiOperation("根据IP查询录像：不许要传数据库ID只需传入设备IP用户名密码就可")
    @PostMapping("/queryRecByIp")
    public BaseResult<VrsRecInfoVo> queryRecByIp(@RequestBody QueryRecByIpVo vo);

}
