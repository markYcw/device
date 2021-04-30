package com.kedacom.device.service;

import com.kedacom.BaseResult;
import com.kedacom.streamMedia.request.QueryrecRequestDTO;
import com.kedacom.streamMedia.request.StartrecRequestDTO;
import com.kedacom.streamMedia.request.StoprecRequestDTO;
import com.kedacom.streamMedia.response.QueryrecResponseVO;
import com.kedacom.streamMedia.response.StartrecResponseVO;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:34
 */
public interface StreamMediaService {

    BaseResult<StartrecResponseVO> startrec(StartrecRequestDTO startrecRequestDTO);

    BaseResult stoprec(StoprecRequestDTO stoprecRequestDTO);

    BaseResult<QueryrecResponseVO> queryrec(QueryrecRequestDTO queryrecRequestDTO);

}
