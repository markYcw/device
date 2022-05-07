package com.kedacom.power.tcp;

import com.kedacom.power.common.Numbers;
import com.kedacom.power.exception.CpException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class TcpChannel {

    /**
     * 发送指令
     *
     * @param socketChannel
     * @param ip
     * @param canWriteMap
     * @param buffer
     * @throws IOException
     */
    public static boolean send(SocketChannel socketChannel, String ip, Map<String, Boolean> canWriteMap, ByteBuffer buffer) throws IOException {
        boolean flag = canWriteMap.get(ip);
        if (flag) {
            socketChannel.write(buffer);
            canWriteMap.put(ip, false);
            return true;
        } else {
            throw new CpException("有未完成的指令，请稍候");
        }
    }

    /**
     * 处理读事件
     *
     * @param channel
     * @return
     */
    public static ByteBuffer handOnRead(Selector selector, SocketChannel channel, String ip, Map<String, Boolean> canWriteMap) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Numbers.CONSTANT_285);
        int readyChannels = selector.select(2000);
        canWriteMap.put(ip, true);
        if (readyChannels <= 0) {
            return null;
        }
        Set<SelectionKey> selectionKeySet = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeySet.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            if (key.isReadable()) {
                //处理OP_READ事件
                SocketChannel sc = (SocketChannel) key.channel();
                if (channel == sc) {
                    channel.read(buffer);
                    iterator.remove();
                }
            }
        }
        return buffer;
    }

    public static ByteBuffer sendAndReceive(SocketChannel socketChannel, String ip, Map<String, Boolean> canWriteMap, ByteBuffer buffer, Selector selector) throws IOException {
        boolean flag = send(socketChannel, ip, canWriteMap, buffer);
        if (flag) {
            ByteBuffer byteBuffer = handOnRead(selector, socketChannel, ip, canWriteMap);
            return byteBuffer;
        }
        return null;
    }
}
