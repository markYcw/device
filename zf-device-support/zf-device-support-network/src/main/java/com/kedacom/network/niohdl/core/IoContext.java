package com.kedacom.network.niohdl.core;


import com.kedacom.exception.KMException;
import com.kedacom.network.niohdl.impl.IoSelectorProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class IoContext {
    private static IoSelectorProvider ioSelector;

    public static IoSelectorProvider getIoSelector() {
        return ioSelector;
    }

    public static void setIoSelector(IoSelectorProvider ioSelector) {
        IoContext.ioSelector = ioSelector;
    }

    public static void initIoSelector() {
        if (ioSelector != null) {
            log.error("ioSelector already init ioSelector {}", ioSelector);
            return;
        }
        try {
            ioSelector = new IoSelectorProvider();
        } catch (IOException e) {
            throw new KMException("init ioSelector failed");
        }
    }

    public static void close(){
        ioSelector.close();
    }
}
