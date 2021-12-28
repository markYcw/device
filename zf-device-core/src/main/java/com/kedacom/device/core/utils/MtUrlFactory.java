package com.kedacom.device.core.utils;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/7
 */
@Slf4j
@Component
public class MtUrlFactory {

    @Value("${zf.kmProxy.server_addr}")
    private String server_addr;

    private String mtHttp = "http://";

    private String mtUri = "/mid/v2/mt";

    @SneakyThrows
    public String getMtRequestUrl() {

        do {
            log.info("mt_server_addr : {}", server_addr);
            Thread.sleep(200);
        } while (StrUtil.isBlank(server_addr));

        return mtHttp + server_addr + mtUri;
    }
}
