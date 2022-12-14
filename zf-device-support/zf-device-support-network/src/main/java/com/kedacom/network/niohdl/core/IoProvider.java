package com.kedacom.network.niohdl.core;

import java.nio.channels.SocketChannel;

public interface IoProvider {
    /**
     * 从channel中接收数据
     * @param channel
     * @param inputHandler
     * @return
     */
    boolean registerInput(SocketChannel channel, InputHandler inputHandler);

    /**
     * 发送数据到channel
     * @param channel
     * @param outputHandler
     * @return
     */
    boolean registerOutput(SocketChannel channel, OutputHandler outputHandler);

    void unRegisterInput(SocketChannel channel);

    void unRegisterOutput(SocketChannel channel);

    abstract class InputHandler implements Runnable {

        @Override
        public final void run() {
            handle();
        }

        protected abstract void handle();
    }

    abstract class OutputHandler implements Runnable {
        // Object attach;
        // public final void setAttach(Object attach) {
        //     this.attach = attach;
        // }
        //
        // public final <T> T getAttach() {
        //     return (T) this.attach;
        // }

        // @Override
        // public final void run() {
        //     handle(attach);
        // }
        //
        // protected abstract void handle(Object attach);
        @Override
        public final void run() {
            handle();
        }

        protected abstract void handle();
    }

    interface IOCallback {
        void onInput(IoArgs args);

        void onOutput();

        void onChannelClosed();
    }
}
