package com.kedacom.network.niohdl.core;


import com.kedacom.network.niohdl.box.StringReceivePacket;
import com.kedacom.network.niohdl.box.StringSendPacket;
import com.kedacom.network.niohdl.impl.SocketChannelAdapter;
import com.kedacom.network.niohdl.impl.async.AsyncReceiveDispatcher;
import com.kedacom.network.niohdl.impl.async.AsyncSendDispatcher;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.SocketChannel;

@Slf4j
public class Connector implements Closeable, SocketChannelAdapter.OnChannelStatusChangedListener {

    private SocketChannel channel;
    private Sender sender;//这两个都引用适配器
    private Receiver receiver;
    private SendDispatcher sendDispatcher;
    private ReceiveDispatcher receiveDispatcher;

    protected void setup(SocketChannel channel) throws IOException {

        this.channel = channel;
        SocketChannelAdapter adapter = new SocketChannelAdapter(channel, IoContext.getIoSelector(), this);
        sender = adapter;
        receiver = adapter;

        sendDispatcher = new AsyncSendDispatcher(sender);
        receiveDispatcher = new AsyncReceiveDispatcher(receiver,receivePacketCallback);

        receiveDispatcher.start();

    }

    public void send(String msg){
        SendPacket packet = new StringSendPacket(msg);
        sendDispatcher.send(packet);
    }


    protected void onReceiveFromCore(String msg) {
        log.info("receive msg : {}", msg);
    }



    //实现Closeable方法
    @Override
    public void close() throws IOException {
        sendDispatcher.close();
        receiveDispatcher.close();
        sender.close();
        receiver.close();
        channel.close();

    }

    //实现SocketChannelAdapter.OnChannelStatusChangedListener中的方法
    @Override
    public void onChannelClosed(SocketChannel channel) {

    }

    //接收AsyncReceiveDispatcher中的回调
    private ReceiveDispatcher.ReceivePacketCallback receivePacketCallback = new ReceiveDispatcher.ReceivePacketCallback() {
        //接收到消息的回调
        @Override
        public void onReceivePacketCompleted(ReceivePacket packet) {
            if(packet instanceof StringReceivePacket){
                String msg = packet.toString();
                onReceiveFromCore(msg);
            }
        }
    };
}
