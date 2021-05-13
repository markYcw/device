package com.kedacom.device.core.convert;

import com.kedacom.acl.network.data.streamMeida.*;
import com.kedacom.streamMedia.request.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Auther: hxj
 * @Date: 2021/5/12 10:12
 */
@Mapper(componentModel = "spring")
public interface StreamMediaConvert {

    StreamMediaConvert INSTANCE = Mappers.getMapper(StreamMediaConvert.class);

    StartRecRequestDTO convertStartRecRequest(StartRecRequest request);

    StopRecRequestDTO convertStopRecRequest(StopRecRequest request);

    QueryRecRequestDTO convertQueryRecRequest(QueryRecRequest request);

    StartAudioMixRequestDTO convertStartAudioMixRequest(StartAudioMixRequest request);

    StopAudioMixRequestDTO convertStopAudioMixRequest(StopAudioMixRequest request);

    UpdateAudioMixRequestDTO convertUpdateAudioMixRequest(UpdateAudioMixRequest request);

    QueryAllAudioMixRequestDTO convertQueryAllAudioMixRequest(QueryAllAudioMixRequest request);

    QueryAudioMixRequestDTO convertQueryAudioMixRequest(QueryAudioMixRequest request);

    StartVideoMixRequestDTO convertStartVideoMixRequest(StartVideoMixRequest request);

    StopVideoMixRequestDTO convertStopVideoMixRequest(StopVideoMixRequest request);

    UpdateVideoMixRequestDTO convertUpdateVideoMixRequest(UpdateVideoMixRequest request);

    QueryVideoMixRequestDTO convertQueryVideoMixRequest(QueryVideoMixRequest request);

}
