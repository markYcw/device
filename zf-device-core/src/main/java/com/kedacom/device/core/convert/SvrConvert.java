package com.kedacom.device.core.convert;

import com.kedacom.device.svr.pojo.DeChnList;
import com.kedacom.device.svr.pojo.EnChnList;
import com.kedacom.device.svr.request.SvrLoginRequest;
import com.kedacom.device.svr.response.*;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.vo.*;
import org.mapstruct.Mapper;

/**
 * @author ycw
 * @date: 2021/09/06 13:46
 * @description svr参数转换接口
 */
@Mapper(componentModel = "spring")
public interface SvrConvert {

    SvrLoginRequest convertToSvrLoginRequest(SvrEntity svrEntity);

    SvrCapVo convertTOSvrCapVo(SvrCapResponse response);

    SvrTimeVo convertTOSvrTimeVo(SvrTimeResponse response);

    EnChnListVo convertToEnChnListVo(EnChnList response);

    CpResetVo convertToCpResetVo(CpResetResponse response);

    DeChnListVo convertToDeChnListVo(DeChnList deChnList);

    DecParamVo convertToDecParamVo(DecParamResponse response);

    RemoteCfgVo convertToRemoteCfgVo(RemoteCfgVoResponse response);

    BurnTaskVo convertTOBurnTaskVo(BurnTaskResponse response);

    RecListVo convertToRecListVo(RecListResponse response);

    GetMergeVo convertToGetMergeVo(GetMergeResponse response);

    GetOsdVo convertToGetOsdVo(GetOsdResponse response);

}
