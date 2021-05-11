package com.kedacom.device.core.service;

import com.kedacom.avIntegration.request.decoder.OsdConfigRequest;
import com.kedacom.avIntegration.request.decoder.OsdDeleteRequest;
import com.kedacom.avIntegration.request.decoder.StyleConfigRequest;
import com.kedacom.avIntegration.request.decoder.StyleQueryRequest;
import com.kedacom.avIntegration.response.decoder.OsdConfigResponse;
import com.kedacom.avIntegration.response.decoder.OsdDeleteResponse;
import com.kedacom.avIntegration.response.decoder.StyleConfigResponse;
import com.kedacom.avIntegration.response.decoder.StyleQueryResponse;

/**
 * @Auther: hxj
 * @Date: 2021/5/11 13:17
 */
public interface DecoderService {

    OsdConfigResponse osdConfig(OsdConfigRequest request);

    OsdDeleteResponse osdDelete(OsdDeleteRequest request);

    StyleQueryResponse styleQuery(StyleQueryRequest request);

    StyleConfigResponse styleConfig(StyleConfigRequest request);

}
