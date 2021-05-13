package com.kedacom.network.niohdl.impl;



import com.kedacom.network.niohdl.core.Receiver;
import com.kedacom.network.niohdl.core.Sender;
import com.kedacom.network.niohdl.core.IoArgs;
import com.kedacom.network.niohdl.core.IoProvider;
import com.kedacom.network.utils.CloseUtil;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;

public class SocketChannelAdapter implements Sender, Receiver, Cloneable {
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    private final SocketChannel channel;
    private final IoProvider provider;
    private final OnChannelStatusChangedListener listener;//指向Connector对象,只有onChannelClosed方法

    // private ioArgs.IoArgsEventListener receiveIoEventListener;//2个都是指向Connector中的echoReceiveListener对象，可借此向Connector回调消息
    // private ioArgs.IoArgsEventListener sendIoEventListener;
    private IoArgs.IoArgsEventProcessor receiveIoEventProcessor;//可借此向Connector回调消息
    private IoArgs.IoArgsEventProcessor sendIoEventProcessor;

    // private ioArgs receiveArgsTemp;

    public SocketChannelAdapter(SocketChannel channel, IoProvider provider,
                                OnChannelStatusChangedListener listener) throws IOException {
        this.channel = channel;
        this.provider = provider;
        this.listener = listener;

        channel.configureBlocking(false);
    }

    @Override
    public void setReceiveListener(IoArgs.IoArgsEventProcessor processor) {
        receiveIoEventProcessor = processor;
    }

    @Override
    public boolean postReceiveAsync() throws IOException {
        if (isClosed.get()) {
            throw new IOException("Current channel is closed!");
        }
        return provider.registerInput(channel, inputHandler);
    }

    @Override
    public void setSendListener(IoArgs.IoArgsEventProcessor processor) {
        sendIoEventProcessor = processor;
    }

    @Override
    public boolean postSendAsync() throws IOException {
        if (isClosed.get()) {
            throw new IOException("Current channel is closed!");
        }
        return provider.registerOutput(channel, outputHandler);
    }

    // @Override
    // public void setReceiveListener(ioArgs.IoArgsEventListener listener) {
    //     receiveIoEventListener = listener;
    // }

    // @Override
    // public boolean receiveAsync(ioArgs args) throws IOException {
    //     if (isClosed.get()) {
    //         throw new IOException("Current channel is closed!");
    //     }
    //     receiveArgsTemp = args;
    //     return provider.registerInput(channel, inputHandler);
    // }

    // @Override
    // public boolean sendAsync(ioArgs args, ioArgs.IoArgsEventListener listener) throws IOException {
    //     if (isClosed.get()) {
    //         throw new IOException("Current channel is closed!");
    //     }
    //
    //     sendIoEventListener = listener;
    //
    //     outputHandler.setAttach(args);
    //     return provider.registerOutput(channel, outputHandler);
    // }

    @Override
    public void close() throws IOException {
        if (isClosed.compareAndSet(false, true)) {
            // 解除注册回调
            provider.unRegisterInput(channel);
            provider.unRegisterOutput(channel);
            // 关闭
            CloseUtil.close(channel);
            // 回调当前Channel已关闭
            listener.onChannelClosed(channel);
        }
    }

    private final IoProvider.InputHandler inputHandler = new IoProvider.InputHandler() {
        @Override
        protected void handle() {
            if (isClosed.get()) {
                return;
            }

            IoArgs.IoArgsEventProcessor processor = receiveIoEventProcessor;
            IoArgs args = processor.providerIoArgs();


            try {
                // 具体的读取操作
                if (args.readFrom(channel) > 0 ) {
                    // 读取完成回调
                    processor.onConsumeCompleted(args);
                } else {
                    processor.onConsumeFailed(args,new IOException("Cannot write any data!"));
                }
            } catch (IOException ignored) {
                CloseUtil.close(SocketChannelAdapter.this);
            }


        }
    };


    private final IoProvider.OutputHandler outputHandler = new IoProvider.OutputHandler() {
        @Override
        protected void handle() {
            if (isClosed.get()) {
                return;
            }
            IoArgs.IoArgsEventProcessor processor = sendIoEventProcessor;
            IoArgs args = processor.providerIoArgs();


            try {
                // 具体的写操作
                if (args.writeTo(channel) > 0) {
                    // 写操作完成回调
                    processor.onConsumeCompleted(args);
                } else {
                    processor.onConsumeFailed(args,new IOException("Cannot write any data!"));
                }
            } catch (IOException ignored) {
                CloseUtil.close(SocketChannelAdapter.this);
            }
        }

    };


    public interface OnChannelStatusChangedListener {
        void onChannelClosed(SocketChannel channel);
    }
}
