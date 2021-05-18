package com.kedacom.core;


import com.alibaba.fastjson.JSONObject;
import com.kedacom.core.handler.ProcessRequests;
import com.kedacom.core.pojo.*;
import com.kedacom.core.spring.NotifyContext;
import com.kedacom.exception.KMProxyException;
import com.kedacom.exception.ParseDataException;
import com.kedacom.exception.UnSupportMsgException;
import com.kedacom.network.niohdl.core.Connector;
import com.kedacom.util.SingletonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class NIOConnector extends Connector {

    private ProcessRequests processRequests;

    private NotifyContext notifyContext;

    private NIOConnMonitor connMonitor;

    /**
     * 默认初始化为未连接状态
     */
    private volatile ConnStatus status = ConnStatus.DIS_CONNECT;



    NIOConnector(SocketChannel socketChannel) throws IOException {

        setup(socketChannel);
        processRequests = SingletonFactory.getInstance(ProcessRequests.class);
        notifyContext = SingletonFactory.getInstance(NotifyContext.class);

    }

    public NotifyContext getNotifyContext() {
        return this.notifyContext;
    }


    @Override
    public void close() throws IOException {
        super.close();
        //processRequests.shutdown();
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


    public CompletableFuture<Response> sendRequest(Request request,Class<?> returnType) {

        //序列化
        String packet = request.packet();

        Integer ssno = request.acquireSsno();

        CompletableFuture<Response> future = new CompletableFuture<>();

        log.info("[ssno:{}]: send msg ------> {}", ssno, packet);

        //发送请求
        send(packet);

        putProcessRequests(ssno,returnType, future);

        return future;

    }

    private void putProcessRequests(Integer requestId,Class<?> returnType, CompletableFuture<Response> future) {

        processRequests.putFuture(requestId, future, returnType);
       // processRequests.putReturnType(requestId, returnType);
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

            handlerResp(msg,respHead);
           // handlerNty(msg, null);

        } else if (jsonObject.containsKey("nty")) {

            NotifyHead ntyHead = jsonObject.getObject("nty", NotifyHead.class);

            log.info("[ssno:{}]: receive nty ------> {}", ntyHead.getSsno(), msg);

            handlerNty(msg, ntyHead);

        } else {
            log.error("cannot read the msg !");
           // throw new UnSupportMsgException("unknown the msg");
        }

    }

    Notify notify = null;
    private void handlerNty(String msg, NotifyHead notifyHead) {

        String name = notifyHead.getName();

        Map<String, Class<?>> notifyMap = notifyContext.getNotifyMap();

        if (CollectionUtils.isEmpty(notifyMap)) {
            throw new KMProxyException("@KmNotify not init !");
        }

        Class<?> clazz = notifyMap.get(name);

        if (clazz == null) {
            throw new KMProxyException(name + " can not match the Class");
        }

        try {
            notify = JSONObject.parseObject(msg, (Type) clazz);

            if (notify != null) {
                notifyContext.publishNotify(notify);

            }
        } catch (Exception e) {
            log.error("parse nty data error ,e: ", e);
           // throw new ParseDataException("parse nty data error");
        }


    }

    Response response = null;
    private void handlerResp(String msg,RespHead respHead) {

        try {

            //测试超时
           // Thread.sleep(10000);

            Class<?> aClass = processRequests.getReturnType(respHead.getSsno());
            response = JSONObject.parseObject(msg, (Type) aClass);
            if (response != null) {
                processRequests.complete(response);
            } else {
                log.error("receive msg data is not resp type :{}", msg);
            }
        } catch (Exception e) {
            log.error("parse resp data error ,e: ", e);
            processRequests.exception(new ParseDataException(e.getMessage(), e), respHead.getSsno());
           // throw new ParseDataException("parse resp data error");
        }

    }

    public static NIOConnector startWith(String serverIp, int serverPort) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();

//      NIOConnMonitor connMonitor = new NIOConnMonitor(socketChannel);
//      connMonitor.start(serverIp, serverPort);

        socketChannel.connect(new InetSocketAddress(InetAddress.getByName(serverIp), serverPort));
        log.info("客户端信息：" + socketChannel.getLocalAddress().toString() + ":" + socketChannel.socket().getLocalPort());
        log.info("服务器信息：" + socketChannel.getRemoteAddress().toString() + ":" + socketChannel.socket().getPort());

        return new NIOConnector(socketChannel);

    }

    public static void connect(SocketChannel socketChannel, String serverIp, int serverPort) throws IOException {

        try {

            socketChannel.connect(new InetSocketAddress(InetAddress.getByName(serverIp), serverPort));

        } catch (IOException e) {

            e.printStackTrace();
        }
        log.info("客户端信息：" + socketChannel.getLocalAddress().toString() + ":" + socketChannel.socket().getLocalPort());
        log.info("服务器信息：" + socketChannel.getRemoteAddress().toString() + ":" + socketChannel.socket().getPort());

    }




}
