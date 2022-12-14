package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.convert.StreamMediaConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.exception.StreamMediaException;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.service.StreamMediaService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import com.kedacom.device.stream.StreamMediaClient;
import com.kedacom.device.stream.request.*;
import com.kedacom.device.stream.response.*;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:35
 */
@Slf4j
@Service
public class StreamMediaServiceImpl implements StreamMediaService {

    @Autowired
    private StreamMediaConvert streamMediaConvert;

    @Resource
    private StreamMediaClient client;

    @Autowired
    private HandleResponseUtil responseUtil;

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public StartRecResponseVO startRec(StartRecDTO request) {
        log.info("开启录像入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        StartRecRequest startRecRequest = streamMediaConvert.convertStartRecRequest(request);
        startRecRequest.setAccount_token("123");
        startRecRequest.setRequest_id("321321");
        startRecRequest.setSsid(ssid);
        log.info("开启录像交互参数:{}", startRecRequest);
        StartRecResponse res = client.startRec(startRecRequest);
        log.info("开启录像应答信息:{}", res);
        String errorMsg = "开启录像失败:{},{},{}";
        responseUtil.handleSMSRes(errorMsg, DeviceErrorEnum.START_REC_FAILED, res);
        return res.acquireData(StartRecResponseVO.class);
    }

    @Override
    public Boolean stopRec(StopRecDTO request) {
        log.info("停止录像入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        StopRecRequest stopRecRequest = streamMediaConvert.convertStopRecRequest(request);
        stopRecRequest.setAccount_token("123");
        stopRecRequest.setRequest_id("321321");
        stopRecRequest.setSsid(ssid);
        log.info("停止录像交互参数:{}", stopRecRequest);
        BaseResponse response = client.stopRec(stopRecRequest);
        log.info("停止录像应答信息:{}", response);
        String error = "停止录像失败:{},{},{}";
        responseUtil.handleSMSRes(error, DeviceErrorEnum.STOP_REC_FAILED, response);
        return true;
    }

    @Override
    public Boolean updateRec(UpdateRecDTO request) {
        log.info("更新录像入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());
        UpdateRecRequest updateRecRequest = streamMediaConvert.convertUpdateRecRequest(request);
        updateRecRequest.setAccount_token("123");
        updateRecRequest.setRequest_id("321321");
        updateRecRequest.setSsid(ssid);
        log.info("更新录像交互参数:{}", updateRecRequest);
        UpdateRecResponse response = client.updateRec(updateRecRequest);
        log.info("更新录像应答信息:{}", response);
        String error = "更新录像失败:{},{},{}";
        responseUtil.handleSMSRes(error, DeviceErrorEnum.UPDATE_REC_FAILED, response);
        return true;

    }

    @Override
    public QueryRecResponseVO queryRec(QueryRecDTO request) {
        String error = "查询录像记录失败:{},{},{}";
        log.info("查询录像记录入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        QueryRecRequest queryRecRequest = streamMediaConvert.convertQueryRecRequest(request);
        queryRecRequest.setSsid(ssid);
        log.info("查询录像记录交互参数:{}", queryRecRequest);
        QueryRecResponse res = client.queryRec(queryRecRequest);
        log.info("查询录像记录应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.QUERY_REC__FAILED, res);
        return res.acquireData(QueryRecResponseVO.class);
    }

    @Override
    public Boolean recKeepAlive(RecKeepAliveDTO request) {
        log.info("录像任务保活入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        RecKeepAliveRequest recKeepAliveRequest = streamMediaConvert.recKeepAlive(request);
        recKeepAliveRequest.setAccountToken("123");
        recKeepAliveRequest.setRequestId("321321");
        recKeepAliveRequest.setSsid(ssid);
        log.info("录像任务保活交互参数:{}", recKeepAliveRequest);
        BaseResponse response = client.recKeepAlive(recKeepAliveRequest);
        log.info("录像任务保活应答信息:{}", response);
        String error = "录像任务保活失败:{},{},{}";
        responseUtil.handleSMSRes(error, DeviceErrorEnum.STOP_REC_FAILED, response);
        return true;
    }

    @Override
    public StartAudioMixResponseVO startAudioMix(StartAudioMixDTO request) {
        String error = "开启音频混音失败:{},{},{}";
        log.info("开启音频混音入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        // 自定义的分组id
        String groupId = UUID.randomUUID().toString().replace("-", "");
        StartAudioMixRequest startAudioMixRequest = streamMediaConvert.convertStartAudioMixRequest(request);
        startAudioMixRequest.setGroupID(groupId);
        startAudioMixRequest.setSsid(ssid);
        log.info("开启音频混音交互参数:{}", startAudioMixRequest);
        StartAudioMixResponse res = client.startAudioMix(startAudioMixRequest);
        log.info("开启音频混音应答:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.START_AUDIO_MIX_FAILED, res);
        StartAudioMixResponseVO response = new StartAudioMixResponseVO();
        response.setMixID(res.getMixID());
        response.setGroupID(groupId);
        log.info("开启音频混音返参:{}", response);
        return response;
    }

    @Override
    public Boolean stopAudioMix(StopAudioMixDTO request) {
        String error = "停止音频混音失败:{},{},{}";
        log.info("停止音频混音入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        StopAudioMixRequest stopAudioMixRequest = streamMediaConvert.convertStopAudioMixRequest(request);
        stopAudioMixRequest.setSsid(ssid);
        log.info("停止音频混音交互参数:{}", stopAudioMixRequest);
        BaseResponse res = client.stopAudioMix(stopAudioMixRequest);
        log.info("停止音频混音应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.STOP_AUDIO_MIX_FAILED, res);
        return true;
    }

    @Override
    public Boolean updateAudioMix(UpdateAudioMixDTO request) {
        String error = "更新音频混音失败:{},{},{}";
        log.info("更新音频混音入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        UpdateAudioMixRequest updateAudioMixRequest = streamMediaConvert.convertUpdateAudioMixRequest(request);
        updateAudioMixRequest.setSsid(ssid);
        log.info("更新音频混音交互参数:{}", updateAudioMixRequest);
        BaseResponse res = client.updateAudioMix(updateAudioMixRequest);
        log.info("更新音频混音应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.UPDATE_AUDIO_MIX_FAILED, res);
        return true;
    }

    @Override
    public QueryAllAudioMixVO queryAllAudioMix(QueryAllAudioMixDTO request) {
        String error = "查询所有混音失败:{},{},{}";
        log.info("查询所有混音入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        QueryAllAudioMixRequest queryAllAudioMixRequest = streamMediaConvert.convertQueryAllAudioMixRequest(request);
        queryAllAudioMixRequest.setSsid(ssid);
        log.info("查询所有混音交互参数:{}", queryAllAudioMixRequest);
        QueryAllAudioMixResponse res = client.queryAllAudioMix(queryAllAudioMixRequest);
        log.info("查询所有混音应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.QUERY_ALL_AUDIO_MIX_FAILED, res);
        return res.acquireData(QueryAllAudioMixVO.class);
    }

    @Override
    public QueryAudioMixResponseVO queryAudioMix(QueryAudioMixDTO request) {
        String error = "查询混音信息失败:{},{},{}";
        log.info("查询混音信息入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        QueryAudioMixRequest queryAudioMixRequest = streamMediaConvert.convertQueryAudioMixRequest(request);
        queryAudioMixRequest.setSsid(ssid);
        log.info("查询混音信息交互参数:{}", queryAudioMixRequest);
        QueryAudioMixResponse res = client.queryAudioMix(queryAudioMixRequest);
        log.info("查询混音信息应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.QUERY_AUDIO_MIX_FAILED, res);
        return res.acquireData(QueryAudioMixResponseVO.class);
    }

    @Override
    public StartVideoMixResponseVO startVideoMix(StartVideoMixDTO request) {
        String error = "开始画面合成失败:{},{},{}";
        log.info("开始画面合成入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        StartVideoMixRequest startVideoMixRequest = streamMediaConvert.convertStartVideoMixRequest(request);
        if (StrUtil.isBlank(request.getGroupID())) {
            String groupId = UUID.randomUUID().toString().replace("-", "");
            startVideoMixRequest.setGroupID(groupId);
        }
        startVideoMixRequest.setSsid(ssid);
        log.info("开始画面合成交互参数:{}", startVideoMixRequest);
        StartVideoMixResponse res = client.startVideoMix(startVideoMixRequest);
        log.info("开始画面合成应答:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.START_VIDEO_MIX_FAILED, res);
        StartVideoMixResponseVO response = new StartVideoMixResponseVO();
        response.setMixID(res.getMixID());
        response.setGroupID(startVideoMixRequest.getGroupID());
        return response;
    }

    @Override
    public Boolean stopVideoMix(StopVideoMixDTO request) {
        String error = "停止画面合成失败:{},{},{}";
        log.info("停止画面合成入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        StopVideoMixRequest stopVideoMixRequest = streamMediaConvert.convertStopVideoMixRequest(request);
        stopVideoMixRequest.setSsid(ssid);
        log.info("停止画面合成交互参数:{}", stopVideoMixRequest);
        BaseResponse res = client.stopVideoMix(stopVideoMixRequest);
        log.info("停止画面合成应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.STOP_VIDEO_MIX_FAILED, res);
        return true;
    }

    @Override
    public Boolean updateVideoMix(UpdateVideoMixDTO request) {
        String error = "流媒体更新画面合成失败:{},{},{}";
        log.info("更新画面合成入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        UpdateVideoMixRequest updateVideoMixRequest = streamMediaConvert.convertUpdateVideoMixRequest(request);
        updateVideoMixRequest.setSsid(ssid);
        log.info("更新画面合成交互参数:{}", updateVideoMixRequest);
        BaseResponse res = client.updateVideoMix(updateVideoMixRequest);
        log.info("更新画面合成应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.UPDATE_VIDEO_MIX_FAILED, res);
        return true;
    }

    @Override
    public QueryAllAudioMixVO queryAllVideoMix(QueryAllVideoMixDTO request) {
        String error = "查询所有画面合成失败:{},{},{}";
        log.info("查询所有画面合成入参信息 :{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        QueryAllVideoMixRequest queryAllVideoMixRequest = new QueryAllVideoMixRequest();
        queryAllVideoMixRequest.setSsid(ssid);
        queryAllVideoMixRequest.setGroupID(request.getGroupID());
        QueryAllAudioMixResponse res = client.queryAllVideoMix(queryAllVideoMixRequest);
        log.info("查询所有画面合成应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.QUERY_ALL_VIDEO_MIX_FAILED, res);
        return res.acquireData(QueryAllAudioMixVO.class);
    }

    @Override
    public QueryVideoMixResponseVO queryVideoMix(QueryVideoMixDTO request) {
        String error = "查询画面信息失败:{},{},{}";
        log.info("查询画面信息入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        QueryVideoMixRequest queryVideoMixRequest = streamMediaConvert.convertQueryVideoMixRequest(request);
        queryVideoMixRequest.setSsid(ssid);
        queryVideoMixRequest.setGroupID(request.getGroupID());
        log.info("查询画面信息交互参数:{}", queryVideoMixRequest);
        QueryVideoMixResponse res = client.queryVideoMix(queryVideoMixRequest);
        log.info("查询画面信息应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.QUERY_VIDEO_MIX_FAILED, res);
        return res.acquireData(QueryVideoMixResponseVO.class);
    }

    @Override
    public Boolean keepVideoMixAlive(VideoMixKeepAliveDTO request) {
        String error = "合成画面保活失败:{},{},{}";
        log.info("合成画面保活入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        VideoMixKeepAliveRequest keepVideoMixAlive = streamMediaConvert.keepVideoMixAlive(request);
        keepVideoMixAlive.setSsid(ssid);
        keepVideoMixAlive.setGroupID(request.getGroupID());
        log.info("合成画面保活交互参数:{}", keepVideoMixAlive);
        VideoMixKeepAliveResponse res = client.keepVideoMixAlive(keepVideoMixAlive);
        log.info("合成画面保活应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.QUERY_VIDEO_MIX_FAILED, res);
        return true;
    }

    @Override
    public Boolean sendTransData(SendTransDataDTO request) {
        String error = "发送透明通道数据失败:{},{},{}";
        log.info("发送透明通道数据入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        SendTransDataRequest sendTransDataRequest = streamMediaConvert.convertSendTransDataDTO(request);
        sendTransDataRequest.setSsid(ssid);
        log.info("发送透明通道数据交互参数:{}", sendTransDataRequest);
        SendTransDataResponse res = client.sendTransData(sendTransDataRequest);
        log.info("发送透明通道数据应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.SEND_TRANS_DATA_FAILED, res);
        return true;
    }

    @Override
    public QueryRealUrlVO queryRealUrl(QueryRealUrlDTO request) {
        String error = "查询实时资源URL失败:{},{},{}";
        log.info("查询实时资源URL入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        QueryRealUrlRequest queryRealUrlRequest = streamMediaConvert.convertQueryRealUrlDTO(request);
        queryRealUrlRequest.setSsid(ssid);
        log.info("查询实时资源URL交互参数:{}", queryRealUrlRequest);
        QueryRealUrlResponse res = client.queryRealUrl(queryRealUrlRequest);
        log.info("查询实时资源URL应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.QUERY_REAL_URL_FAILED, res);
        return res.acquireData(QueryRealUrlVO.class);
    }

    @Override
    public QueryHistoryUrlVO queryHistoryUrl(QueryHistoryUrlDTO request) {
        String error = "查询历史资源URL失败:{},{},{}";
        log.info("查询历史资源URL入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        QueryHistoryUrlRequest queryHistoryUrlRequest = streamMediaConvert.convertQueryHistoryUrlDTO(request);
        queryHistoryUrlRequest.setSsid(ssid);
        log.info("查询历史资源URL交互参数:{}", queryHistoryUrlRequest);
        QueryHistoryUrlResponse res = client.queryHistoryUrl(queryHistoryUrlRequest);
        log.info("查询历史资源UR应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.QUERY_HISTORY_URL_FAILED, res);
        return res.acquireData(QueryHistoryUrlVO.class);
    }

    @Override
    public Boolean sendOrderData(SendOrderDataDTO request) {
        String error = "发送宏指令数据失败:{},{},{}";
        log.info("发送宏指令数据入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        SendOrderDataRequest sendOrderDataRequest = streamMediaConvert.convertSendOrderDataDTO(request);
        sendOrderDataRequest.setSsid(ssid);
        log.info("发送宏指令数据交互参数:{}", sendOrderDataRequest);
        SendOrderDataResponse res = client.sendOrderData(sendOrderDataRequest);
        log.info("发送宏指令数据应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.SEND_TRANS_DATA_FAILED, res);
        return true;
    }

    @Override
    public GetAudioCapVO getAudioCap(GetAudioCapDTO request) {
        String error = "获取音频能力集失败:{},{},{}";
        log.info("获取音频能力集入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        GetAudioCapRequest getAudioCapRequest = streamMediaConvert.convertGetAudioCapDTO(request);
        getAudioCapRequest.setSsid(ssid);
        log.info("获取音频能力集交互参数:{}", getAudioCapRequest);
        GetAudioCapResponse res = client.getAudioCap(getAudioCapRequest);
        log.info("获取音频能力集应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.GET_AUDIO_CAP_FAILED, res);
        GetAudioCapVO vo = new GetAudioCapVO();
        vo.setAudCapNum(res.getAudCapNum());
        vo.setAudMicInNum(res.getAudMicInNum());
        vo.setAudLineInNum(res.getAudLineInNum());
        vo.setAudJackPhoneInNum(res.getAudJackPhoneInNum());
        vo.setAudDMicInNum(res.getAudDMicInNum());
        vo.setAudDviInNum(res.getAudDviInNum());
        vo.setAudAtboxNetChnNum(res.getAudAtboxNetChnNum());
        vo.setAudNetChnNum(res.getAudNetChnNum());
        vo.setAudLocalNetChnNum(res.getAudLocalNetChnNum());
        vo.setAudRemNetChnNum(res.getAudRemNetChnNum());
        vo.setAudIn(res.getAudIn());
        return vo;
    }

    @Override
    public Boolean ctrlAudioAct(CtrlAudioActDTO request) {
        String error = "控制音频功率上报失败:{},{},{}";
        log.info("控制音频功率上报入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        CtrlAudioActRequest ctrlAudioActRequest = streamMediaConvert.convertCtrlAudioActDTO(request);
        ctrlAudioActRequest.setSsid(ssid);
        log.info("控制音频功率上报交互参数:{}", ctrlAudioActRequest);
        CtrlAudioActResponse res = client.ctrlAudioAct(ctrlAudioActRequest);
        log.info("控制音频功率上报应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.CTRL_AUDIO_ACT_FAILED, res);
        return true;
    }

    @Override
    public Boolean setAudioActInterval(SetAudioActIntervalDTO request) {
        String error = "设置音频功率上报间隔失败:{},{},{}";
        log.info("设置音频功率上报间隔入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        SetAudioActIntervalRequest setAudioActIntervalRequest = streamMediaConvert.convertSetAudioActIntervalDTO(request);
        setAudioActIntervalRequest.setSsid(ssid);
        log.info("设置音频功率上报间隔交互参数:{}", setAudioActIntervalRequest);
        SetAudioActIntervalResponse res = client.setAudioActInterval(setAudioActIntervalRequest);
        log.info("设置音频功率上报间隔应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.SET_AUDIO_ACT_INTERVAL_FAILED, res);
        return true;
    }

    @Override
    public GetBurnStateVO getBurnState(GetBurnStateDTO request) {
        String error = "刻录状态请求失败:{},{},{}";
        log.info("刻录状态请求入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        GetBurnStateRequest getBurnStateRequest = streamMediaConvert.convertGetBurnStateDTO(request);
        getBurnStateRequest.setSsid(ssid);
        log.info("刻录状态请求交互参数:{}", getBurnStateRequest);
        GetBurnStateResponse res = client.getBurnState(getBurnStateRequest);
        log.info("刻录状态请求应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.GET_BURN_STATE_FAILED, res);
        GetBurnStateVO vo = res.acquireData(GetBurnStateVO.class);
        return vo;
    }

    @Override
    public DeviceInfoEntity getBySsid(Integer ssid) {
        log.info("根据ssid查询流媒体实体接口入参:{}", ssid);
        LambdaQueryWrapper<DeviceInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceInfoEntity::getSessionId, ssid);
        List<DeviceInfoEntity> list = deviceMapper.selectList(wrapper);
        return list.get(DevTypeConstant.getZero);
    }

    @Override
    public GetSvrAudioActStateVo getSvrAudioActState(GetSvrAudioActStateDTO dto) {
        String error = "获取当前语音激励状态失败:{},{},{}";
        log.info("获取当前语音激励状态入参信息:{}", dto);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(dto.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());
        GetSvrAudioActStateRequest request = new GetSvrAudioActStateRequest();
        BeanUtils.copyProperties(dto, request);
        request.setSsid(ssid);
        log.info("获取当前语音激励状态交互参数:{}", request);
        GetSvrAudioActStateResponse res = client.getSvrAudioActState(request);
        log.info("获取当前语音激励状态应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.GET_SVR_AUDIO_ACT_STATE_FAILED, res);
        GetSvrAudioActStateVo vo = res.acquireData(GetSvrAudioActStateVo.class);
        return vo;
    }

    @Override
    public StartPushUrlVO startPushUrl(StartPushUrlDTO request) {
        String error = "开始推送媒体流失败:{},{},{}";
        log.info("开始推送媒体流入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        StartPushUrlRequest startPushUrlRequest = streamMediaConvert.convertStartPushUrlDTO(request);
        startPushUrlRequest.setSsid(ssid);
        log.info("开始推送媒体流失败交互参数:{}", startPushUrlRequest);
        StartPushUrlResponse res = client.startPushUrl(startPushUrlRequest);
        log.info("开始推送媒体流失败交互参数应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.SEND_TRANS_DATA_FAILED, res);
        return res.acquireData(StartPushUrlVO.class);
    }

    @Override
    public Boolean stopPushUrl(StopPushUrlDTO request) {
        String error = "停止推送媒体流失败:{},{},{}";
        log.info("停止推送媒体流入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        StopPushUrlRequest stopPushUrlRequest = streamMediaConvert.convertStopPushUrlDTO(request);
        stopPushUrlRequest.setSsid(ssid);
        log.info("发送宏指令数据交互参数:{}", stopPushUrlRequest);
        StopPushUrlResponse res = client.stopPushUrl(stopPushUrlRequest);
        log.info("发送宏指令数据应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.SEND_TRANS_DATA_FAILED, res);
        return true;
    }

    @Override
    public StartPullUrlVO startPullUrl(StartPullUrlDTO request) {
        String error = "开始拉取媒体流失败:{},{},{}";
        log.info("开始拉取媒体流入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        StartPullUrlRequest startPullUrlRequest = streamMediaConvert.convertStartPullUrlDTO(request);
        startPullUrlRequest.setSsid(ssid);
        log.info("开始拉取媒体流交互参数:{}", startPullUrlRequest);
        StartPullUrlResponse res = client.startPullUrl(startPullUrlRequest);
        log.info("开始拉取媒体流应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.SEND_TRANS_DATA_FAILED, res);
        return res.acquireData(StartPullUrlVO.class);
    }

    @Override
    public Boolean stopPullUrl(StopPullUrlDTO request) {
        String error = "停止拉取媒体流失败:{},{},{}";
        log.info("停止拉取媒体流失败:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        StopPullUrlRequest stopPullUrlRequest = streamMediaConvert.convertStopPullUrlDTO(request);
        stopPullUrlRequest.setSsid(ssid);
        log.info("停止拉取媒体流交互参数:{}", stopPullUrlRequest);
        StopPullUrlResponse res = client.stopPullUrl(stopPullUrlRequest);
        log.info("停止拉取媒体流应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.SEND_TRANS_DATA_FAILED, res);
        return true;
    }

    @Override
    public QueryMeetRecVO queryMeetRec(QueryMeetRecDTO request) {
        String error = "查询会议录像记录失败:{},{},{}";
        log.info("查询会议录像记录入参信息:{}", request);
        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());
        QueryMeetRecRequest queryMeetRecRequest = streamMediaConvert.convertQueryMeetRecDTO(request);
        queryMeetRecRequest.setSsid(ssid);
        log.info("查询会议录像记录交互参数:{}", queryMeetRecRequest);
        QueryMeetRecResponse res = client.recMeetQuery(queryMeetRecRequest);
        log.info("查询会议录像记录应答信息:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.QUERY_MEET_REC_FAILED, res);
        return res.acquireData(QueryMeetRecVO.class);
    }

    @Override
    public StartMeetRecResponseVO startMeetRec(StartMeetRecDTO request) {
        log.info("开启会议录像入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        StartMeetRecRequest recMeetRequest = streamMediaConvert.convertStartRecMeetRequest(request);
        recMeetRequest.setAccount_token("123");
        recMeetRequest.setRequest_id("321321");
        recMeetRequest.setSsid(ssid);
        log.info("开启会议录像交互参数:{}", recMeetRequest);
        StartMeetRecResponse res = client.startRecMeet(recMeetRequest);
        log.info("开启会议录像应答信息:{}", res);
        String errorMsg = "开启会议录像失败:{},{},{}";
        responseUtil.handleSMSRes(errorMsg, DeviceErrorEnum.START_MEET_REC_FAILED, res);
        return res.acquireData(StartMeetRecResponseVO.class);
    }

    @Override
    public Boolean stopMeetRec(StopMeetRecDTO request) {
        log.info("停止会议录像入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        StopMeetRecRequest recRequest = streamMediaConvert.convertStopStopMeetRecRequest(request);
        recRequest.setAccount_token("123");
        recRequest.setRequest_id("321321");
        recRequest.setSsid(ssid);
        log.info("停止会议录像交互参数:{}", recRequest);
        StopMeetRecResponse response = client.stopMeetRec(recRequest);
        log.info("停止会议录像应答信息:{}", response);
        String error = "停止会议录像失败:{},{},{}";
        responseUtil.handleSMSRes(error, DeviceErrorEnum.STOP_MEET_REC_FAILED, response);
        return true;
    }

    @Override
    public Boolean updateMeetRec(UpdateMeetRecDTO request) {
        log.info("更新会议录像入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());
        UpdateMeetRecRequest recRequest = streamMediaConvert.convertUpdateMeetRecRequest(request);
        recRequest.setAccount_token("123");
        recRequest.setRequest_id("321321");
        recRequest.setSsid(ssid);
        log.info("更新会议录像交互参数:{}", recRequest);
        UpdateMeetRecResponse response = client.updateMeetRec(recRequest);
        log.info("更新会议录像应答信息:{}", response);
        String error = "更新会议录像失败:{},{},{}";
        responseUtil.handleSMSRes(error, DeviceErrorEnum.UPDATE_MEET_REC_FAILED, response);
        return true;
    }

    @Override
    public Boolean meetRecKeepAlive(MeetRecKeepAliveDTO request) {
        log.info("录像任务保活入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        MeetRecKeepAliveRequest aliveRequest = streamMediaConvert.covertMeetRecKeepAliveRequest(request);
        aliveRequest.setAccountToken("123");
        aliveRequest.setRequestId("321321");
        aliveRequest.setSsid(ssid);
        log.info("录像任务保活交互参数:{}", aliveRequest);
        MeetRecKeepAliveResponse response = client.meetRecKeepAlive(aliveRequest);
        log.info("录像任务保活应答信息:{}", response);
        String error = "录像任务保活失败:{},{},{}";
        responseUtil.handleSMSRes(error, DeviceErrorEnum.MEET_REC_KEEP_ALIVE_FAILED, response);
        return true;
    }

    @Override
    public QueryMeetRecTaskVO queryMeetRecTask(QueryMeetRecTaskDTO request) {
        log.info("查询会议录像任务入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        QueryMeetRecTaskRequest taskRequest = streamMediaConvert.covertQueryMeetRecTaskRequest(request);
        taskRequest.setAccountToken("123");
        taskRequest.setRequestId("321321");
        taskRequest.setSsid(ssid);
        log.info("查询会议录像任务交互参数:{}", taskRequest);
        QueryMeetRecTaskResponse response = client.queryMeetRecTask(taskRequest);
        log.info("查询会议录像任务应答信息:{}", response);
        String error = "查询会议录像任务失败:{},{},{}";
        responseUtil.handleSMSRes(error, DeviceErrorEnum.QUERY_MEET_REC_TASK_FAILED, response);
        return response.acquireData(QueryMeetRecTaskVO.class);
    }

    @Override
    public Boolean deleteMeetRec(DeleteMeetRecDTO request) {
        log.info("删除会议记录入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        DeleteMeetRecRequest recRequest = streamMediaConvert.covertDeleteMeetRecRequest(request);
        recRequest.setAccountToken("123");
        recRequest.setRequestId("321321");
        recRequest.setSsid(ssid);
        log.info("删除会议记录交互参数:{}", recRequest);
        DeleteMeetRecResponse response = client.deleteMeetRec(recRequest);
        log.info("删除会议记录应答信息:{}", response);
        String error = "删除会议记录失败:{},{},{}";
        responseUtil.handleSMSRes(error, DeviceErrorEnum.DELETE_MEET_REC_FAILED, response);
        return true;
    }

    @Override
    public Boolean meetRecConfig(MeetRecConfigDTO request) {
        log.info("会议录像全局配置入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        MeetRecordConfigRequest configRequest = streamMediaConvert.covertMeetRecordConfigRequest(request);
        configRequest.setAccountToken("123");
        configRequest.setRequestId("321321");
        configRequest.setSsid(ssid);
        log.info("会议录像全局配置交互参数:{}", configRequest);
        MeetRecordConfigResponse response = client.meetRecordConfig(configRequest);
        log.info("会议录像全局配置应答信息:{}", response);
        String error = "会议录像全局配置失败:{},{},{}";
        responseUtil.handleSMSRes(error, DeviceErrorEnum.MEET_REC_CONFIG_FAILED, response);
        return true;
    }

    @Override
    public QueryMeetRecConfigVO queryMeetRecConfig(QueryMeetRecConfigDTO request) {
        log.info("查询全局会议录像配置入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        QueryMeetRecordConfigRequest configRequest = streamMediaConvert.covertQueryMeetRecordConfigRequest(request);
        configRequest.setAccountToken("123");
        configRequest.setRequestId("321321");
        configRequest.setSsid(ssid);
        log.info("查询全局会议录像配置交互参数:{}", configRequest);
        QueryMeetRecordConfigResponse response = client.queryMeetRecordConfig(configRequest);
        log.info("查询全局会议录像配置应答信息:{}", response);
        String error = "查询全局会议录像配置失败:{},{},{}";
        responseUtil.handleSMSRes(error, DeviceErrorEnum.QUERY_MEET_REC_CONFIG_FAILED, response);
        return response.acquireData(QueryMeetRecConfigVO.class);
    }

    @Override
    public QueryMeetRecCapVO queryMeetRecCap(QueryMeetRecCapDTO request) {
        log.info("容量存储查询入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        QueryMeetRecordCapRequest capRequest = streamMediaConvert.covertQueryMeetRecordCapRequest(request);
        capRequest.setAccountToken("123");
        capRequest.setRequestId("321321");
        capRequest.setSsid(ssid);
        log.info("容量存储查询交互参数:{}", capRequest);
        QueryMeetRecordCapResponse response = client.queryMeetRecordCap(capRequest);
        log.info("容量存储查询应答信息:{}", response);
        String error = "容量存储查询失败:{},{},{}";
        responseUtil.handleSMSRes(error, DeviceErrorEnum.QUERY_MEET_REC_CONFIG_FAILED, response);
        return response.acquireData(QueryMeetRecCapVO.class);
    }

    @Override
    public Boolean meetRecEventSub(MeetRecEventSubDTO request) {
        log.info("事件订阅入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)) {
            throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), "请输入正确的统一平台id");
        }
        Integer ssid = Integer.valueOf(deviceInfoEntity.getSessionId());

        MeetRecEventSubRequest subRequest = streamMediaConvert.covertMeetRecEventSubRequest(request);
        subRequest.setAccountToken("123");
        subRequest.setRequestId("321321");
        subRequest.setSsid(ssid);
        log.info("事件订阅交互参数:{}", subRequest);
        BaseResponse response = client.meetRecEventSub(subRequest);
        log.info("事件订阅应答信息:{}", response);
        String error = "事件订阅失败:{},{},{}";
        responseUtil.handleSMSRes(error, DeviceErrorEnum.EVENT_SUB_FAILED, response);
        return true;
    }
}
