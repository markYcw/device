package com.kedacom.network.niohdl;


import com.kedacom.network.niohdl.core.*;

import java.nio.channels.SocketChannel;

public abstract class API implements IoProvider.IOCallback {
    private SocketChannel socketChannel;
    private SendDispatcher sendDispatcher;
    private ReceiveDispatcher receiveDispatcher;
    private ReceiveDispatcher.ReceivePacketCallback receivePacketCallback = new ReceiveDispatcher.ReceivePacketCallback() {
        //接收到消息的回调
        @Override
        public void onReceivePacketCompleted(ReceivePacket packet) {

        }
    };

    @Override
    public void onInput(IoArgs args) {
        onReceiveFromCore(args);
    }

    protected void onReceiveFromCore(IoArgs args){
    }

    @Override
    public void onOutput() {

    }

    @Override
    public void onChannelClosed() {
        IoContext.getIoSelector().unRegisterInput(socketChannel);
        IoContext.getIoSelector().unRegisterOutput(socketChannel);
    }
}
