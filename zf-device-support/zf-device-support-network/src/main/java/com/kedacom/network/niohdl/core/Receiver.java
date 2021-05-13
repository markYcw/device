package com.kedacom.network.niohdl.core;

import java.io.Closeable;
import java.io.IOException;

public interface Receiver extends Closeable {
    void setReceiveListener(IoArgs.IoArgsEventProcessor processor);
    boolean postReceiveAsync() throws IOException;
}
