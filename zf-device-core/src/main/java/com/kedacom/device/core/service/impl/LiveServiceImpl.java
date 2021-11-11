package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.kedacom.BaseResult;
import com.kedacom.cu.dto.LiveStreamDto;
import com.kedacom.cu.dto.StreamDto;
import com.kedacom.cu.dto.VodStreamDto;
import com.kedacom.cu.vo.*;
import com.kedacom.device.core.constant.BrowseStreamParam;
import com.kedacom.device.core.convert.CuConvert;
import com.kedacom.device.core.convert.LiveConvert;
import com.kedacom.device.core.service.LiveService;
import com.kedacom.device.core.utils.RemoteRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/11
 */
@Slf4j
@Service
public class LiveServiceImpl implements LiveService {

    @Resource
    private RemoteRestTemplate remoteRestTemplate;

    @Override
    public BaseResult<LiveStreamResponseVo> liveStream(LiveStreamRequestVo requestVo) {

        log.info("发布直播请求参数 : {}", requestVo);
        LiveStreamDto liveStreamDto = LiveConvert.INSTANCE.convertLiveStreamDto(requestVo);
        String url = BrowseStreamParam.PREFIX_OF_URL + BrowseStreamParam.LIVE_STREAM;
        LiveStreamResponseVo responseVo = null;
        try {
            responseVo = remoteRestTemplate.getRestTemplate()
                    .postForObject(url, JSON.toJSONString(liveStreamDto), LiveStreamResponseVo.class);
        } catch (RuntimeException e) {
            log.error("发布直播失败, message : {}", e.getMessage());
        }
        assert responseVo != null;
        if (StrUtil.isBlank(responseVo.getUrl())) {
            return BaseResult.failed("发布直播失败，返回 url 异常");
        }

        return BaseResult.succeed(null, responseVo);
    }

    @Override
    public BaseResult<VodStreamResponseVo> vodStream(VodStreamRequestVo requestVo) {

        log.info("发布直播请求参数 : {}", requestVo);
        VodStreamDto vodStreamDto = LiveConvert.INSTANCE.convertVodStreamDto(requestVo);
        String url = BrowseStreamParam.PREFIX_OF_URL + BrowseStreamParam.VOD_STREAM;
        VodStreamResponseVo responseVo = null;
        try {
            responseVo = remoteRestTemplate.getRestTemplate()
                    .postForObject(url, JSON.toJSONString(vodStreamDto), VodStreamResponseVo.class);
        } catch (RuntimeException e) {
            log.error("发布点播失败, message : {}", e.getMessage());
        }
        assert responseVo != null;
        if (BrowseStreamParam.VOD_SUCCEED.equals(responseVo.getError())) {

            return BaseResult.succeed(null, responseVo);
        } else if (BrowseStreamParam.VOD_ERROR_CODE.equals(responseVo.getError())) {

            return BaseResult.failed("发布点播失败, 该设备不在线请检查");
        }

        return BaseResult.failed("发布点播失败, 没有找到符合调节的录像");
    }

    @Override
    public BaseResult<StreamResponseVo> stream(StreamRequestVo requestVo) {

        log.info("发布rtsp请求参数 : {}", requestVo);
        StreamDto streamDto = LiveConvert.INSTANCE.convertStreamDto(requestVo);
        String url = BrowseStreamParam.PREFIX_OF_URL + BrowseStreamParam.STREAM;
        StreamResponseVo responseVo = null;
        try {
            responseVo = remoteRestTemplate.getRestTemplate()
                    .postForObject(url, JSON.toJSONString(streamDto), StreamResponseVo.class);
        } catch (RuntimeException e) {
            log.error("发布rtsp失败, message : {}", e.getMessage());
        }
        assert responseVo != null;
        if (StrUtil.isBlank(responseVo.getUrl())) {
            return BaseResult.failed("发布rtsp失败，返回 url 异常");
        }

        return BaseResult.succeed(null, responseVo);
    }

}
