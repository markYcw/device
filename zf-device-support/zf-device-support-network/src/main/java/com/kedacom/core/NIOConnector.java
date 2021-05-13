package com.kedacom.core;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kedacom.core.handler.ProcessRequests;
import com.kedacom.core.spring.NotifyContext;
import com.kedacom.device.*;
import com.kedacom.exception.UnSupportMsgException;
import com.kedacom.network.niohdl.core.Connector;
import com.kedacom.util.SingletonFactory;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
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
        System.out.println("连接已关闭无法读取数据");
    }


    public CompletableFuture<Response> sendRequest(Request request) {

        Integer ssno = request.getSsno();

        //序列化
        String json = JSON.toJSONString(request);

        CompletableFuture<Response> future = new CompletableFuture<>();

        log.info("[ssno:{}]: send msg ------> {}", ssno, json);
        //发送请求
        send(json);

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

    private void handlerResp(String msg) {

        Object obj = JSONObject.parse(msg);

        if (obj instanceof Response) {
            processRequests.complete((Response) obj);
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
