package com.kedacom.core;


import com.alibaba.fastjson.JSONObject;
import com.kedacom.core.handler.ProcessRequests;
import com.kedacom.core.pojo.*;
import com.kedacom.core.spring.NotifyContext;
import com.kedacom.exception.ConnectionException;
import com.kedacom.exception.ParseDataException;
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

    private ConnectorListenerManager listenerManager;

    private SocketChannel socketChannel;

    private String serverIp;

    private Integer serverPort;



    /**
     * 默认初始化为未连接状态
     */
    private volatile ConnStatus status = ConnStatus.DIS_CONNECT;


    public NIOConnector(){

    }

    public synchronized NIOConnector initConnector(String serverIp, int serverPort) throws IOException {

        processRequests = SingletonFactory.getInstance(ProcessRequests.class);

        notifyContext = SingletonFactory.getInstance(NotifyContext.class);

        listenerManager = ConnectorListenerManager.getInstance();

       // socketChannel = SocketChannel.open();

        this.serverIp = serverIp;

        this.serverPort = serverPort;

        initConnMonitor(serverIp, serverPort);

//        NIOConnMonitor connMonitor = new NIOConnMonitor(socketChannel, this);
//
//        connMonitor.start(serverIp, serverPort);

//        socketChannel.connect(new InetSocketAddress(InetAddress.getByName(serverIp), serverPort));
//        log.info("客户端信息：" + socketChannel.getLocalAddress().toString() + ":" + socketChannel.socket().getLocalPort());
//        log.info("服务器信息：" + socketChannel.getRemoteAddress().toString() + ":" + socketChannel.socket().getPort());

        return this;

    }

    /**
     * 初始化连接监控组件
     * @param serverIp 服务端IP
     * @param serverPort 服务端端口
     */
    private synchronized void initConnMonitor(String serverIp, int serverPort) {

        log.info("initConnMonitor");

        //初始化连接状态为正在连接中
        status = ConnStatus.CONNECTING;

        if (connMonitor == null || !connMonitor.isConnected()) {
            connMonitor = new NIOConnMonitor( this);
            log.info("start ConnMonitor");
            connMonitor.start(serverIp, serverPort);
        }

    }

    /**
     * 真正进行网络连接的方法
     * @param serverIp 服务端IP
     * @param serverPort 服务端端口
     * @throws IOException
     */
    public boolean connect(String serverIp, int serverPort) throws IOException {

        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(InetAddress.getByName(serverIp), serverPort));
        } catch (IOException e) {
            log.error("connect to server failed ,serverIp {} serverPort {} ,caused by ", serverIp, serverPort, e);

            status = ConnStatus.FAILED_CONNECT;

            //发布连接失败通知
            publishConnectEvent(status, serverIp, serverPort);

            return false;
        }

        //只有网络连接成功之后才可以setup
        setup(socketChannel);

        log.info("客户端信息：" + socketChannel.getLocalAddress().toString() + ":" + socketChannel.socket().getLocalPort());
        log.info("服务器信息：" + socketChannel.getRemoteAddress().toString() + ":" + socketChannel.socket().getPort());

        //连接成功
        status = ConnStatus.CONNECTED;

        //发布连接成功事件
        publishConnectEvent(status, serverIp, serverPort);

        return true;
    }

    public NotifyContext getNotifyContext() {

        return this.notifyContext;
    }


    @Override
    public void close() throws IOException {
        super.close();
        if (connMonitor != null) {
            connMonitor.close();
        }
        log.info("close network resources");
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
        log.error("channel is closed ,cannot read the data ,ready retry to connect serverIp {} serverPort {}", serverIp, serverPort);
        //将连接置为未连接状态
        status = ConnStatus.DIS_CONNECT;

        if (connMonitor != null) {
            connMonitor.close();
        }

        //发布断开连接事件
        publishConnectEvent(status, serverIp, serverPort);

        //开始重新初始化连接监控组件
        initConnMonitor(serverIp, serverPort);
    }

    /**
     * 发布通知
     * @param status 连接状态
     * @param serverIp 服务端IP
     * @param serverPort 服务端Port
     */
    private void publishConnectEvent(ConnStatus status, String serverIp, Integer serverPort) {
        try {
            if (null != listenerManager) {
                listenerManager.publish(status, serverIp + ":" + serverPort);
            }
        } catch (Exception e) {

            log.error("publish connStatus event failed status [{}] ! e", status.toString(), e);
        }

    }


    public CompletableFuture<Response> sendRequest(Request request,Class<?> returnType) {

        // 判断连接状态
        if (ConnStatus.CONNECTED != status) {
            log.error("cur connect status is {}", status);
            throw new ConnectionException("connection is not connected  serverIp " + serverIp + " serverPort " + serverPort);
        }

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
            log.error("cannot read the msg ! msg is {}", msg);
           // throw new UnSupportMsgException("unknown the msg");
        }

    }

    Notify notify = null;
    private void handlerNty(String msg, NotifyHead notifyHead) {

        String name = notifyHead.getName();

        Map<String, Class<?>> notifyMap = notifyContext.getNotifyMap();

        if (CollectionUtils.isEmpty(notifyMap)) {
            log.error("@KmNotify not init ! and nty {}", msg);
            return;
            //throw new KMProxyException("@KmNotify not init !");
        }

        Class<?> clazz = notifyMap.get(name);

        if (clazz == null) {
           // throw new KMProxyException(name + " can not match the Class");
            log.error("{} can not match the Class , nty {}", name, msg);
            return;
        }

        try {
            notify = JSONObject.parseObject(msg, (Type) clazz);

            if (notify != null) {
                notifyContext.publishNotify(notify);

            }
        } catch (Exception e) {
            log.error("parse nty data error ,e: ", e);
            //TODO 发布异常处理
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
                log.error("receive msg data is not resp type ssno is [{}] msg :{}", respHead.getSsno(), msg);
            }
        } catch (Exception e) {
            log.error("parse resp data error ssno is [{}], e: ", respHead.getSsno(), e);
            processRequests.exception(new ParseDataException("[ ssno : "+respHead.getSsno() +" ] "+ e.getMessage(), e), respHead.getSsno());
           // throw new ParseDataException("parse resp data error");
        }

    }






}
