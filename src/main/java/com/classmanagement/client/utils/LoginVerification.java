package com.classmanagement.client.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;


/**
 * ClassManger
 *
 * @author Richard-J
 * @description 登陆验证
 * @date 2019.03
 */
public class LoginVerification {

    /**
     * description 获得cookie及成功码
     *
     * @param userName
     * @param pwd
     * @return java.lang.String
     */
    public static String simulateLogin(String userName, String pwd) throws Exception {
        pwd = DigestUtils.md5Hex(pwd);

        PrintWriter out = null;
        BufferedReader in = null;
        String cookie = "";
        URL realUrl = new URL("http://bkjws.sdu.edu.cn/b/ajaxLogin");
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(conn.getOutputStream());
        // 发送请求参数
        out.print("j_username=" + userName + "&j_password=" + pwd);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = in.readLine();
        String success = "success";
        if (!line.contains(success)) {
            return null;
        } else {
            cookie = conn.getHeaderField("Set-Cookie");
            cookie = cookie.split(";")[0];
        }
        return cookie;
    }

    /** 
     * description getJSON 
     *
     * @param cookie 
     * @return java.lang.String 
     */
    public static String getJSON(String cookie) throws Exception {
        //创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建httpGet请求
        HttpGet httpGet = new HttpGet("http://bkjws.sdu.edu.cn/b/grxx/xs/xjxx/detail");
        httpGet.setHeader("Cookie", cookie);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //获取返回实体
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "utf-8");
        httpClient.close();
        return result;
    }
}
