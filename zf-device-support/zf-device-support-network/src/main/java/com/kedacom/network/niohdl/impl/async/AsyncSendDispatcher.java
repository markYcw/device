package com.kedacom.network.niohdl.impl.async;


import com.kedacom.network.niohdl.core.SendDispatcher;
import com.kedacom.network.niohdl.core.SendPacket;
import com.kedacom.network.niohdl.core.Sender;
import com.kedacom.network.niohdl.core.IoArgs;
import com.kedacom.network.utils.CloseUtil;
import com.kedacom.util.ByteUtil;

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class AsyncSendDispatcher implements SendDispatcher, IoArgs.IoArgsEventProcessor {
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    private Sender sender;
    private Queue<SendPacket> queue = new ConcurrentLinkedDeque<>();
    private AtomicBoolean isSending = new AtomicBoolean();
    private IoArgs ioArgs = new IoArgs();
    private SendPacket<?> packetTemp;
    //当前发送的packet大小以及进度
    private long total;
    private long position;

    private ReadableByteChannel channel;

    private int headLength = 12;

    private byte[] head;

    private String version = "SFV1R1";


    public AsyncSendDispatcher(Sender sender) {
        this.sender = sender;
        sender.setSendListener(this);
    }

    /**
     * connector将数据封装进packet后，调用这个方法
     *
     * @param packet
     */
    @Override
    public void send(SendPacket packet) {
        queue.offer(packet);//将数据放进队列中
        if (isSending.compareAndSet(false, true)) {
            sendNextPacket();
        }
    }

    @Override
    public void cancel(SendPacket packet) {

    }

    /**
     * 从队列中取数据
     *
     * @return
     */
    private SendPacket takePacket() {
        SendPacket packet = queue.poll();
        if (packet != null && packet.isCanceled()) {
            //已经取消不用发送
            return takePacket();
        }
        return packet;
    }

    private void sendNextPacket() {
        SendPacket temp = packetTemp;
        if (temp != null) {
            CloseUtil.close(temp);
        }
        SendPacket packet = packetTemp = takePacket();
        if (packet == null) {
            //队列为空，取消发送状态
            isSending.set(false);
            return;
        }

        total = packet.length();
        position = 0;

        sendCurrentPacket();
    }

    private void sendCurrentPacket() {

        if (position >= total) {
            completePacket(position == total);
            sendNextPacket();
            return;
        }

        try {
            //发送当前包
            sender.postSendAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 一个packet发送完毕
     *
     * @param isSucceed
     */
    private void completePacket(boolean isSucceed) {
        SendPacket packet = this.packetTemp;
        if (packet == null) {
            return;
        }
        CloseUtil.close(packet, channel);
        packetTemp = null;
        channel = null;
        total = 0;
        position = 0;
        head = null;
    }

    private void closeAndNotify() {
        CloseUtil.close(this);
    }

    @Override
    public void close() {
        if (isClosed.compareAndSet(false, true)) {
            isSending.set(false);
            //异常导致的完成操作
            completePacket(false);
        }
    }

    /**
     * 接收回调,来自writeHandler输出线程
     */
    // private ioArgs.IoArgsEventListener ioArgsEventListener = new ioArgs.IoArgsEventListener() {
    //     @Override
    //     public void onStarted(ioArgs args) {
    //
    //     }
    //
    //     @Override
    //     public void onCompleted(ioArgs args) {
    //         //继续发送当前包packetTemp，因为可能一个包没发完
    //         sendCurrentPacket();
    //     }
    // };

    private void buildHead(int bodyLength) {
        byte[] versionByte = version.getBytes();

        //包头要求的剩余长度
        int remainLength = this.headLength - versionByte.length;

        byte[] dataByte = ByteUtil.int2byteArray(bodyLength, remainLength);

        head = ByteUtil.merger(versionByte, dataByte);
    }


    /**
     * 将数据写入ioArgs
     *
     * @return
     */
    @Override
    public IoArgs providerIoArgs() {
        IoArgs args = ioArgs;
        if (channel == null) {
            //首包
            //将数据装入channel，open()返回inputStream类型
            channel = Channels.newChannel(packetTemp.open());

            int length = (int) packetTemp.length();
            buildHead(length);
            args.setLimit(headLength);
           // args.writeLength((int) packetTemp.length());
            args.writeHead(head);
        } else {
            args.setLimit((int) Math.min(args.capacity(), total - position));

            try {
                int count = args.readFrom(channel);
                position += count;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return args;

        // args.startWriting();//将ioArgs缓冲区中的指针设置好
        //
        // if (position >= total) {
        //     sendNextPacket();
        //     return;
        // } else if (position == 0) {
        //     //首包，需要携带长度信息
        //     args.writeLength(total);
        // }
        //
        // byte[] bytes = packetTemp.bytes();
        // //把bytes的数据写入到IoArgs中
        // int count = args.readFrom(bytes, position);
        // position += count;
        //
        // //完成封装
        // args.finishWriting();//flip()操作
        // //向通道注册OP_write，将Args附加到runnable中；selector线程监听到就绪即可触发线程池进行消息发送
        //
        // sender.setSendListener(this);
        // return null;
    }

    @Override
    public void onConsumeFailed(IoArgs args, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onConsumeCompleted(IoArgs args) {
        sendCurrentPacket();
    }
}
