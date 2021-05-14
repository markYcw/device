package com.kedacom.core.proxy;

import com.kedacom.core.NIOConnector;
import com.kedacom.core.pojo.Request;
import com.kedacom.core.pojo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletableFuture;

/**
 * @author van.shu
 * @date 2021/5/12 7:59
 */
@Slf4j
public class NetworkProxy implements InvocationHandler, FactoryBean<Object> {

    private NIOConnector connector;

    private Class<?> type;

    public NetworkProxy() {

    }

    public NetworkProxy(NIOConnector connector, Class<?> type) {

        this.connector = connector;

        this.type = type;

    }


    /**
     * 创建并获取代理对象
     *
     * @param <T>
     * @return
     */
    public  <T> T newInstance() {
        ClassLoader classLoader = type.getClassLoader();
        //Class[] classes = {type};
        T proxy = (T) Proxy.newProxyInstance(classLoader, new Class<?>[]{type}, this);
        return proxy;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if ("equals".equals(method.getName())) {
            try {
                Object otherHandler =
                        args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
                return equals(otherHandler);
            } catch (IllegalArgumentException e) {
                return false;
            }
        } else if ("hashCode".equals(method.getName())) {
            return hashCode();
        } else if ("toString".equals(method.getName())) {
            return toString();
        }

        log.info("我来代理发请求啦，参数是 args :{}", args);

        if (args != null) {
            for (Object arg : args) {
                if (arg instanceof Request) {

                    CompletableFuture<Response> future = connector.sendRequest((Request) arg);

                    return future.get();
                }

            }
        }
        return null;
    }

    @Override
    public Object getObject() throws Exception {

        Object proxy = newInstance();
        log.info("proxy = {}", proxy);
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

}
