package com.kedacom.protocol;


import cn.hutool.core.util.StrUtil;
import com.kedacom.exception.ProtocolException;
import com.kedacom.util.ByteUtil;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 默认网络协议实现
 * @author van.shu
 * @create 2021/4/23 14:31
 */
public abstract class SplitHeadBodyProtocol<T> implements SocketProtocol<T> {

    /**
     * 默认是UTF-8字符集
     */
    static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    /**
     * 协议版本号
     */
    private String version = "SFV1R1";

    /**
     * 协议头总共12个字节，前6个字节为版本号，后6个字节为消息体长度（不含协议头数据）
     * 长度不足6位补0 如132--->000132，最大999999
     */
    private int headLength = 12;

    /**
     * 消息体字节长度
     */
    private int bodyLength;


    /**
     * 字符集
     */
    private Charset charset = DEFAULT_CHARSET;


    @Override
    public ByteBuffer encode(T body) {

        String bodyStr = buildBody(body);

        byte[] bodyByte = bodyStr.getBytes(charset);

        this.bodyLength = bodyByte.length;

        byte[] headByte = buildHeader();

        byte[] packet = ByteUtil.merger(headByte, bodyByte);

        return ByteBuffer.wrap(packet);

    }


    @Override
    public T decode(ByteBuffer buffer) {


        //粘包  半包

        return null;
    }

    @Override
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public Charset getCharset() {
        return this.charset;
    }


    private byte[] buildHeader(){

        byte[] versionByte = version.getBytes(charset);

        //包头要求的剩余长度
        int remainLength = this.headLength - versionByte.length;

        byte[] dataByte = ByteUtil.int2byteArray(this.bodyLength, remainLength);

        return ByteUtil.merger(versionByte, dataByte);

    }


    /**
     * 解析包体
     * @param data
     * @return
     */
    public abstract T parseBody(String data);


    /**
     * 构建包体
     * @param obj
     * @return
     */
    public abstract String buildBody(T obj);


    void setVersion(String version) {

        if (StrUtil.isEmpty(version)) {

            throw new ProtocolException("protocol version can not be empty");
        }

        this.version = version;
    }

    void setHeadLength(int headLength) {
        this.headLength = headLength;
    }





}
