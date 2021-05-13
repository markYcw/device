package com.kedacom.network.niohdl.core;


import com.kedacom.network.niohdl.impl.IoSelectorProvider;

public class IoContext {
    private static IoSelectorProvider ioSelector;

    public static IoSelectorProvider getIoSelector() {
        return ioSelector;
    }

    public static void setIoSelector(IoSelectorProvider ioSelector) {
        IoContext.ioSelector = ioSelector;
    }

    public static void close(){
        ioSelector.close();
    }
}
