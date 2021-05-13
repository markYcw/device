package com.kedacom.core.proxy;

import com.kedacom.core.config.NetworkConfig;
import com.kedacom.network.client.NIOConnector;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author van.shu
 * @date 2021/5/12 7:59
 */
@Slf4j
public class NetworkProxy implements InvocationHandler {


    private NIOConnector connector;

    private NetworkConfig config;


    public NetworkProxy(NIOConnector connector, NetworkConfig config) {

        this.connector = connector;
        this.config = config;

    }


    /**
     * 创建并获取代理对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T newInstance(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        connector.sendRequest();

        return null;
    }
}
