package com.kedacom.acl;


import com.kedacom.device.stream.StreamMediaClient;
import com.kedacom.device.stream.request.*;
import com.kedacom.device.stream.response.*;
import org.springframework.stereotype.Service;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 19:43
 */
@Service
public class StreamMediaClientImpl implements StreamMediaClient {
    @Override
    public StartRecResponse startRec(StartRecDTO startRecDTO) {
        return null;
    }

    @Override
    public StreamMediaResponse stopRec(StopRecDTO stoprecDTO) {
        return null;
    }

    @Override
    public QueryRecResponse queryRec(QueryRecDTO queryrecDTO) {
        return null;
    }

    @Override
    public StartAudioMixResponse startAudioMix(StartAudioMixDTO startAudioMixDTO) {
        return null;
    }

    @Override
    public StreamMediaResponse stopAudioMix(StopAudioMixDTO stopAudioMixDTO) {
        return null;
    }

    @Override
    public StreamMediaResponse updateAudioMix(UpdateAudioMixDTO updateAudioMixDTO) {
        return null;
    }

    @Override
    public QueryAllAudioMixResponse queryAllAudioMix(QueryAllAudioMixDTO queryAllAudioMixDTO) {
        return null;
    }

    @Override
    public QueryAudioMixResponse queryAudioMix(QueryAudioMixDTO queryAudioMixDTO) {
        return null;
    }

    @Override
    public StartVideoMixResponse startVideoMix(StartVideoMixDTO startVideoMixDTO) {
        return null;
    }

    @Override
    public StreamMediaResponse stopVideoMix(StopVideoMixDTO stopVideoMixDTO) {
        return null;
    }

    @Override
    public StreamMediaResponse updateVideoMix(UpdateVideoMixDTO updateVideoMixDTO) {
        return null;
    }

    @Override
    public QueryAllAudioMixResponse queryAllVideoMix(QueryAllVideoMixDTO queryAllVideoMixDTO) {
        return null;
    }

    @Override
    public QueryVideoMixResponse queryVideoMix(QueryVideoMixDTO queryVideoMixDTO) {
        return null;
    }
}
