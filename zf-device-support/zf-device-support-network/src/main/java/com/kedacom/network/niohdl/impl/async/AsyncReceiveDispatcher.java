package com.kedacom.network.niohdl.impl.async;


import com.kedacom.network.niohdl.box.StringReceivePacket;
import com.kedacom.network.niohdl.core.ReceiveDispatcher;
import com.kedacom.network.niohdl.core.ReceivePacket;
import com.kedacom.network.niohdl.core.Receiver;
import com.kedacom.network.niohdl.core.IoArgs;
import com.kedacom.network.utils.CloseUtil;
import com.kedacom.util.ByteUtil;


import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class AsyncReceiveDispatcher implements ReceiveDispatcher, IoArgs.IoArgsEventProcessor {
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    private final Receiver receiver;
    private final ReceivePacketCallback callback;
    private IoArgs args = new IoArgs();
    private ReceivePacket<?> packetTemp;
    private byte[] buffer;
    private long total;
    private long position;

    private WritableByteChannel channel;

    private int headLength = 12;

    private byte[] head = new byte[headLength];

    private int bodyLength;

    private String version = "SFV1R1";

    public AsyncReceiveDispatcher(Receiver receiver, ReceivePacketCallback callback) {
        this.receiver = receiver;
        this.receiver.setReceiveListener(this);
        this.callback = callback;
    }

    /**
     * connector中调用该方法进行
     */
    @Override
    public void start() {
        registerReceive();
    }

    private void registerReceive() {

        try {
            receiver.postReceiveAsync();
        } catch (IOException e) {
            closeAndNotify();
        }
    }

    private void closeAndNotify() {
        CloseUtil.close(this);
    }

    @Override
    public void stop() {

    }

    @Override
    public void close() throws IOException {
        if (isClosed.compareAndSet(false, true)) {
            completePacket(false);
        }
    }


    // private ioArgs.IoArgsEventListener ioArgsEventListener = new ioArgs.IoArgsEventListener() {
    //     @Override
    //     public void onStarted(ioArgs args) {
    //         int receiveSize;
    //         if (packetTemp == null) {
    //             receiveSize = 4;
    //         } else {
    //             receiveSize = Math.min(total - position, args.capacity());
    //         }
    //         //设置接受数据大小
    //         args.setLimit(receiveSize);
    //     }
    //
    //     @Override
    //     public void onCompleted(ioArgs args) {
    //         assemblePacket(args);
    //         //继续接受下一条数据，因为可能同一个消息可能分隔在两份IoArgs中
    //         registerReceive();
    //     }
    // };

    private int parseHead() {
        int versionLength = version.getBytes().length;
        byte[] versionByteArr = Arrays.copyOfRange(head, 0, versionLength);

        if (checkVersion(versionByteArr)) {
            int remainLength = headLength - versionLength;
            byte[] remainBytes = new byte[remainLength];
            System.arraycopy(head, versionLength, remainBytes, 0, remainLength);
            return ByteUtil.byteArray2Int(remainBytes);
        }
        return 0;
    }

    private boolean checkVersion(byte[] checkByteArr) {

        String checkVersion = new String(checkByteArr);

        return version.equals(checkVersion);

    }

    /**
     * 解析数据到packet
     *
     * @param args
     */
    private void assemblePacket(IoArgs args) {
        if (packetTemp == null) {
            //int length = args.readLength();
            args.readHead(head);
            int length = parseHead();
            packetTemp = new StringReceivePacket(length);
            channel = Channels.newChannel(packetTemp.open());

            buffer = new byte[length];
            total = length;
            position = 0;
        }


        try {
            int count = args.writeTo(channel);

            position += count;

            if (position == total) {
                completePacket(true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void completePacket(boolean isSucceed) {
        ReceivePacket packet = this.packetTemp;
        CloseUtil.close(packet);
        packetTemp = null;

        WritableByteChannel channel = this.channel;
        CloseUtil.close(channel);
        this.channel = null;
        if (packet != null) {
            callback.onReceivePacketCompleted(packet);
        }
    }

    //下面三个方法实现ioArgs中的接口IoArgsEventProcessor
    @Override
    public IoArgs providerIoArgs() {
        IoArgs args = this.args;
        int receiveSize;
        if (packetTemp == null) {
            receiveSize = headLength;
        } else {
            receiveSize = (int) Math.min(total - position, args.capacity());
        }
        //设置接受数据大小
        args.setLimit(receiveSize);
        return args;
    }

    @Override
    public void onConsumeFailed(IoArgs args, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onConsumeCompleted(IoArgs args) {
        assemblePacket(args);
        registerReceive();
    }
}
