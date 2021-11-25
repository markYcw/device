package com.kedacom.device.core.service;

import com.kedacom.BaseResult;
import com.kedacom.cu.vo.*;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/11
 */
public interface LiveService {

    /**
     * 发布直播
     * @param requestVo
     * @return
     */
    BaseResult<LiveStreamResponseVo> liveStream(LiveStreamRequestVo requestVo);

    /**
     * 发布点播
     * @param requestVo
     * @return
     */
    BaseResult<VodStreamResponseVo> vodStream(VodStreamRequestVo requestVo);

    /**
     * 发布rtsp源
     * @param requestVo
     * @return
     */
    BaseResult<StreamResponseVo> stream(StreamRequestVo requestVo);

}
