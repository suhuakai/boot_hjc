package com.tg.admin.common.note.utils;

import com.alibaba.fastjson.JSONObject;
import com.tg.admin.common.exception.RRException;
import org.apache.http.Consts;
import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NoteUtils {
    /**
     * 业务ID，易盾根据产品业务特点分配
     */
    private final static String BUSINESSID = "d9114778e4e84b3f9574a4de3f2f7a1f";
    /**
     * 产品密钥ID，产品标识
     */
    private final static String SECRETID = "a952e8afd9477f2fca2e12ea014ecf72";
    /**
     * 产品私有密钥，服务端生成签名信息使用，请严格保管，避免泄露
     */
    private final static String SECRETKEY = "81ef271d106eb1031b87daeaa90886de";

    /**
     * 接口地址
     */
    private final static String API_URL = "https://sms.dun.163yun.com/v2/sendsms";

    /**
     * 实例化HttpClient，发送http请求使用，可根据需要自行调参
     */
    private static HttpClient httpClient = HttpClient4Utils.createHttpClient(100, 20, 10000, 2000, 2000);

    public static void main(String[] agre) {
        try {
            send("17612776382", "12346");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void send(String mobile, String code) throws Exception {
        // 此处用申请通过的模板id
        String templateId = "12376";
        // 模板参数对应的json格式数据,例如模板为您的验证码为${p1},请于${p2}时间登陆到我们的服务器
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("time", "20180816");
        String params = jsonObject.toJSONString();
        Map<String, String> datas = buildParam(mobile, templateId, params);
        String result = HttpClient4Utils.sendPost(httpClient, API_URL, datas, Consts.UTF_8);
        String c = JSONObject.parseObject(result).getString("code");
        System.out.println("result = [" + result + "]");
        if (c.equals("506")) {
            throw new RRException("今日，短信已上线");
        } else if (c.equals("200")) {

        } else {
            throw new RRException("短信发送失败！");
        }
    }

    private static Map<String, String> buildParam(String mobile, String templateId, String params) throws IOException {
        Map map = new HashMap<String, String>();
        map.put("businessId", BUSINESSID);
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("version", "v2");
        map.put("templateId", templateId);
        map.put("mobile", mobile);
        // 国际短信对应的国际编码(非国际短信接入请注释掉该行代码)
        // map.put("internationalCode", "对应的国家编码");
        map.put("paramType", "json");
        map.put("params", params);
        map.put("nonce", UUID.randomUUID().toString().replace("-", ""));
        map.put("secretId", SECRETID);
        String sign = SignatureUtils.genSignature(SECRETKEY, map);
        map.put("signature", sign);
        return map;
    }

}
