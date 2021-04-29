package com.kedacom.protocol;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * socket编解码协议
 *
 * @author van.shu
 * @create 2021/4/23 15:15
 */
public interface SocketProtocol<T> {


    /**
     * 编码
     * @param data 数据
     * @return buffer
     */
    ByteBuffer encode(T data);


    /**
     * 解码
     *
     * @param buffer buffer
     * @return  数据
     */
    T decode(ByteBuffer buffer);


    /**
     * 设置字符集
     * @param charset 字符集
     */
    void setCharset(Charset charset);


    /**
     * 获取字符集
     * @return 字符集
     */
    Charset getCharset();


}
