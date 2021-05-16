package com.kedacom.core;


import com.alibaba.fastjson.JSONObject;
import com.kedacom.core.handler.ProcessRequests;
import com.kedacom.core.pojo.*;
import com.kedacom.core.spring.NotifyContext;
import com.kedacom.exception.UnSupportMsgException;
import com.kedacom.network.niohdl.core.Connector;
import com.kedacom.util.SingletonFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class NIOConnector extends Connector {

    private ProcessRequests processRequests;

    private NotifyContext notifyContext;

    NIOConnector(SocketChannel socketChannel) throws IOException {

        setup(socketChannel);
        processRequests = SingletonFactory.getInstance(ProcessRequests.class);
        notifyContext = SingletonFactory.getInstance(NotifyContext.class);
    }


    @Override
    protected void onReceiveFromCore(String msg) {
        super.onReceiveFromCore(msg);
        handlerMsg(msg);

    }

    @Override
    public void onChannelClosed(SocketChannel channel) {
        super.onChannelClosed(channel);
        log.error("连接已关闭无法读取数据");
    }


    public CompletableFuture<Response> sendRequest(Request request) {

        Integer ssno = request.acquireSsno();

        //序列化
        String packet = request.packet();

        CompletableFuture<Response> future = new CompletableFuture<>();

        log.info("[ssno:{}]: send msg ------> {}", ssno, packet);
        //发送请求
        send(packet);

        putProcessRequests(ssno, future);

        return future;

    }

    private void putProcessRequests(Integer requestId, CompletableFuture<Response> future) {

        processRequests.put(requestId, future);
    }


    /**
     * 处理数据
     *
     * @param msg 数据
     */
    private void handlerMsg(String msg) {

        JSONObject jsonObject = JSONObject.parseObject(msg);


        if (jsonObject.containsKey("resp")) {
            //响应处理
            RespHead respHead = jsonObject.getObject("resp", RespHead.class);

            log.info("[ssno:{}]: receive resp ------> {}", respHead.getSsno(), msg);

            handlerResp(msg);

        } else if (jsonObject.containsKey("nty")) {

            NotifyHead ntyHead = jsonObject.getObject("nty", NotifyHead.class);

            log.info("[ssno:{}]: receive nty ------> {}", ntyHead.getSsno(), msg);

            handlerNty(msg);

        } else {
            log.error("cannot read the msg !");
            throw new UnSupportMsgException("unknown the msg");
        }

    }

    private void handlerNty(String msg) {

        Object nty = JSONObject.parse(msg);

        if (nty instanceof Notify) {
            //发布通知
            notifyContext.publishNotify((Notify) nty);
        }else {
            log.error("receive msg data is not nty type :{}", msg);
        }


    }

    BaseResponse response = null;

    private void handlerResp(String msg) {

        //Class<?> aClass = map.get(ssno);
        //response = JSONObject.parseObject(resStr, (Type) aClass);
        if (response != null) {
            processRequests.complete(response);
        } else {
            log.error("receive msg data is not resp type :{}", msg);
        }


    }


    public static NIOConnector startWith(String serverIp, int serverPort) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(InetAddress.getByName(serverIp), serverPort));

        System.out.println("客户端信息：" + socketChannel.getLocalAddress().toString() + ":" + socketChannel.socket().getLocalPort());
        System.out.println("服务器信息：" + socketChannel.getRemoteAddress().toString() + ":" + socketChannel.socket().getPort());

        return new NIOConnector(socketChannel);
    }


}
