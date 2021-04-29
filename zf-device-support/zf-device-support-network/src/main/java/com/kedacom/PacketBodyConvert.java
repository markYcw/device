package com.kedacom;

import com.kedacom.protocol.SplitHeadBodyProtocol;

/**
 * 用于业务的对象转化转换，屏蔽不必要的信息参数
 * @author van.shu
 * @create 2021/4/25 17:28
 */
public class PacketBodyConvert<T> extends SplitHeadBodyProtocol<T> {


    @Override
    public T parseBody(String data) {
        return null;
    }

    @Override
    public String buildBody(T obj) {
        return null;
    }

}
