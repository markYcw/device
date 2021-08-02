package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.ObjectUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

        String groupId = UUID.randomUUID().toString().replace("-", "");
        StartVideoMixRequest startVideoMixRequest = streamMediaConvert.convertStartVideoMixRequest(request);
        startVideoMixRequest.setGroupID(groupId);
        startVideoMixRequest.setSsid(ssid);
        log.info("开始画面合成交互参数:{}", startVideoMixRequest);
        StartVideoMixResponse res = client.startVideoMix(startVideoMixRequest);
        log.info("开始画面合成应答:{}", res);
        responseUtil.handleSMSRes(error, DeviceErrorEnum.START_VIDEO_MIX_FAILED, res);
        StartVideoMixResponseVO response = new StartVideoMixResponseVO();
        response.setMixID(res.getMixID());
        response.setGroupID(groupId);
        return response;
    }

    @Override
    public Boolean stopVideoMix(StopVideoMixDTO request) {
        String error = "停止画面合成失败:{},{},{}";
        log.info("停止画面合成入参信息:{}", request);

        DeviceInfoEntity deviceInfoEntity = deviceMapper.selectById(request.getUmsId());
        if (ObjectUtil.isNull(deviceInfoEntity)){
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
        if (ObjectUtil.isNull(deviceInfoEntity)){
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
}
