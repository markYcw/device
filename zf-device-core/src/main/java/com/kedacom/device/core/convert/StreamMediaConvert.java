package com.kedacom.device.core.convert;

import com.kedacom.device.stream.request.*;
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

    StartRecDTO convertStartRecRequest(StartRecRequest request);

    StopRecDTO convertStopRecRequest(StopRecRequest request);

    QueryRecDTO convertQueryRecRequest(QueryRecRequest request);

    StartAudioMixDTO convertStartAudioMixRequest(StartAudioMixRequest request);

    StopAudioMixDTO convertStopAudioMixRequest(StopAudioMixRequest request);

    UpdateAudioMixDTO convertUpdateAudioMixRequest(UpdateAudioMixRequest request);

    QueryAllAudioMixDTO convertQueryAllAudioMixRequest(QueryAllAudioMixRequest request);

    QueryAudioMixDTO convertQueryAudioMixRequest(QueryAudioMixRequest request);

    StartVideoMixDTO convertStartVideoMixRequest(StartVideoMixRequest request);

    StopVideoMixDTO convertStopVideoMixRequest(StopVideoMixRequest request);

    UpdateVideoMixDTO convertUpdateVideoMixRequest(UpdateVideoMixRequest request);

    QueryVideoMixDTO convertQueryVideoMixRequest(QueryVideoMixRequest request);

}
