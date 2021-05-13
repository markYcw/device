package com.kedacom.device.core.service;

import com.kedacom.avIntegration.request.scheme.SchemeConfigRequest;
import com.kedacom.avIntegration.request.scheme.SchemeQueryRequest;
import com.kedacom.acl.network.data.avIntegration.scheme.SchemeConfigResponse;
import com.kedacom.acl.network.data.avIntegration.scheme.SchemeQueryResponse;

/**
 * @Auther: hxj
 * @Date: 2021/5/11 19:39
 */
public interface SchemeService {

    SchemeConfigResponse config(SchemeConfigRequest request);

    SchemeQueryResponse query(SchemeQueryRequest request);

}
