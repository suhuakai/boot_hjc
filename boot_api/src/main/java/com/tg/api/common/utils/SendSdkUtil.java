package com.tg.api.common.utils;

import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;


public class SendSdkUtil {
    //账号
    private static final String ID = "utoken";
    //密码
    private static final String pwd = "yunaibo520zxc";
    //请求url
    private static final String HTTPURL = "http://service.winic.org:8009/sys_port/gateway/index.asp";


    public static String HTTP_POST(String url, String Data) throws Exception {
        BufferedReader In = null;
        PrintWriter Out = null;
        HttpURLConnection HttpConn = null;
        try {
            URL urls = new URL(url);
            HttpConn = (HttpURLConnection) urls.openConnection();
            HttpConn.setRequestMethod("POST");
            HttpConn.setDoInput(true);
            HttpConn.setDoOutput(true);

            Out = new PrintWriter(HttpConn.getOutputStream());
            Out.println(Data);
            Out.flush();

            if (HttpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuffer content = new StringBuffer();
                String tempStr = "";
                In = new BufferedReader(new InputStreamReader(HttpConn.getInputStream()));
                while ((tempStr = In.readLine()) != null) {
                    content.append(tempStr);
                }
                In.close();
                return content.toString();
            } else {
                throw new Exception("HTTP_POST_ERROR_RETURN_STATUS：" + HttpConn.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Out.close();
            HttpConn.disconnect();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
         send("18651645072","856955");
    }

    public static String send(String phone, String code) throws Exception {
        HTTP_POST(HTTPURL, "id=" + ID + "&pwd=" + pwd + "&to=" + phone + "&content=" + URLEncoder.encode("您好,DX助手验证码：" + code + "  有效时间5分钟【DX助手】", "GB2312") + "&time=");
        return code;
    }

    public static String sendPassword(String phone, String code) throws Exception {
        HTTP_POST(HTTPURL, "id=" + ID + "&pwd=" + pwd + "&to=" + phone + "&content=" + URLEncoder.encode("您好,您的DX交易密码为：" + code + "  【DX助手】", "GB2312") + "&time=");
        return code;
    }
}
