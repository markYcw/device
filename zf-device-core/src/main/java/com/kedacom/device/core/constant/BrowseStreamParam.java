package com.kedacom.device.core.constant;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/11
 */
public interface BrowseStreamParam {

    /**
     * 发布直播，点播，rtsp源 url前缀
     */
    String PREFIX_OF_URL = "http://127.0.0.1:8227/";

    /**
     * 直播
     */
    String LIVE_STREAM = "devlivestream";

    /**
     * 点播
     */
    String VOD_STREAM = "devvodstream";

    /**
     * rtsp
     */
    String STREAM = "stream";

    String VOD_SUCCEED = "0";

    String VOD_ERROR_CODE = "50072";

}
