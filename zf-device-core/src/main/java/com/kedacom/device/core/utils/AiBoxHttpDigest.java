package com.kedacom.device.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/22
 */
@Slf4j
public class AiBoxHttpDigest {

    /**
     * 摘要认证 两次请求
     * @param url
     * @return 返回结果
     */
    public static String doPostDigest(String url, String username, String password, String paramJson) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = null;
        String strResponse = null;
        try {
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            // 构造请求头
            httpPost.setHeader("Content-type", "application/VIID+JSON;charset=UTF-8");
            httpPost.setHeader("User-Identify", "10101010101010101010");
            //设置缓存
            httpPost.addHeader("Cache-Control", "no-cache");
            httpPost.setHeader("Connection", "Close");
            RequestConfig.Builder builder = RequestConfig.custom();
            //设置请求时间
            builder.setSocketTimeout(3000);
            //设置超时时间
            builder.setConnectTimeout(5000);
            //设置是否跳转链接(反向代理)
            builder.setRedirectsEnabled(false);
            // 设置 连接 属性
            httpPost.setConfig(builder.build());
            StringEntity stringEntity = new StringEntity(paramJson, "utf-8");
            httpPost.setEntity(stringEntity);
            // 执行请求
            httpResponse = httpClient.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();
            // 检验返回码
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println("第一次发送摘要认证 Post请求 返回码:{}"+ statusCode);
            if (401 == statusCode) {
                strResponse = EntityUtils.toString(responseEntity, "utf-8");
                log.info("Post请求401返回结果 : {}", strResponse);
                // 组织参数，发起第二次请求
                Header[] headers = httpResponse.getHeaders("WWW-Authenticate");
                HeaderElement[] elements = headers[0].getElements();
                String realm = null;
                String qop = null;
                String nonce = null;
                String opaque = null;
                String method = "POST";
                String uri = "/NVR/CompareSimilarity";
                for (HeaderElement element : elements) {
                    if (element.getName().equals("Digest realm")) {
                        realm = element.getValue();
                    } else if (element.getName().equals("qop")) {
                        qop = element.getValue();
                    } else if (element.getName().equals("nonce")) {
                        nonce = element.getValue();
                    } else if (element.getName().equals("opaque")) {
                        opaque = element.getValue();
                    }
                }
                // 以上为 获取第一次请求后返回的 数据
                String nc = "00000001";
                // cnonce 可随机生成
                String cnonce = "uniview";
                // 后期变成可配置
                String a1 = username + ":" + realm + ":" + password;
                String a2 = method + ":" + uri;
                String response = null;
                try {
                    response = DigestUtils.md5Hex((DigestUtils.md5Hex(a1.getBytes("UTF-8")) + ":" + nonce + ":" + nc
                            + ":" + "uniview" + ":" + qop + ":" + DigestUtils.md5Hex(a2.getBytes("UTF-8"))).getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    System.out.println("MD5异常:{}"+ e.getLocalizedMessage());
                }
                String authorization = "Digest realm=" + realm + ",username=" + username + ",nonce=" + nonce + ",uri=" + uri
                        + ",qop=" + qop + ",opaque=" + opaque + ",response=" + response + ",nc=" + nc + ",cnonce=" + cnonce;
                httpPost.addHeader("Authorization", authorization);
                // 发送第二次请求
                httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                int statusCode1 = httpResponse.getStatusLine().getStatusCode();
                System.out.println("第二次发送摘要认证 Post请求 返回码:{}"+statusCode1);
                if (HttpStatus.SC_OK == statusCode1) {
                    strResponse = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                    log.info("第二次发送strResponse : {}", strResponse);
                    return strResponse;
                } else {
                    strResponse = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                    log.info("第二次鉴权认证请求非 200 返回结果 : {}", strResponse);
                    return strResponse;
                }
            } else {
                strResponse = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                log.info("第一次鉴权认证请求非401 返回结果 : {}", strResponse);
            }
        } catch (Exception e) {
            log.error("摘要认证 发送请求失败 : {}", e.getLocalizedMessage());
        } finally {
            if (null != httpPost) {
                httpPost.releaseConnection();
            }
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    log.error("httpResponse 流关闭异常 : ", e);
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("httpClient 流关闭异常 : ", e);
                }
            }
        }
        return strResponse;
    }
}
