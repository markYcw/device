package com.kedacom.acl.network.unite;

import com.kedacom.BaseResult;
import com.kedacom.streamMedia.request.QueryrecRequestDTO;
import com.kedacom.streamMedia.request.StartrecRequestDTO;
import com.kedacom.streamMedia.request.StoprecRequestDTO;
import com.kedacom.streamMedia.response.QueryrecResponseVO;
import com.kedacom.streamMedia.response.StartrecResponseVO;

/**
 * 流媒体接口
 *
 * @author van.shu
 * @create 2021/4/26 17:30
 */
public interface StreamMediaInterface {

    BaseResult<StartrecResponseVO> startrec(String ssid, StartrecRequestDTO startrecRequestDTO);

    BaseResult stoprec(String ssid, StoprecRequestDTO stoprecRequestDTO);

    BaseResult<QueryrecResponseVO> queryrec(String ssid, QueryrecRequestDTO queryrecRequestDTO);

}
