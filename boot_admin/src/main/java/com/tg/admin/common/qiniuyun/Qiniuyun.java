package com.tg.admin.common.qiniuyun;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

public class Qiniuyun {

    private static String ACCESS_KEY = "R8F_6y9TDc1JELcSaUH0ofowg04SR9DKtNjunZ_v";

    private static String SECRET_KEY = "eFLJCSyZysNwmDKIAHK32mFQmvFc2O1RMT7dDky8";

    //cdn.szlzpt.com

    private static Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
    private static PutPolicy putPolicy;
    private static PutExtra extra = new PutExtra();

    public static String upInput(String key, InputStream inputStream) throws Exception {
        String bucketName = "lvzhouyp123:" + key;
        putPolicy = new PutPolicy(bucketName);
        String uptoken = putPolicy.token(mac);
        PutRet ret = IoApi.Put(uptoken, key, inputStream, extra);
        return ret.getKey();
    }

    public static String upFile(String key, File file) throws Exception {
        String bucketName = "lvzhouyp123:" + key;
        putPolicy = new PutPolicy(bucketName);
        String uptoken = putPolicy.token(mac);
        PutRet ret = IoApi.putFile(uptoken, key, file, extra);
        return ret.getKey();
    }


    public static void main(String[] agre) {
        File file2 = new File("D:\\tg\\img\\HOT-16H详情图1.jpg");
        System.out.println("检擦==================="+file2.exists());
        try {
            System.out.println(UUID.randomUUID().toString() + "1234.jpg".substring("1234.jpg".lastIndexOf(".")));
            System.out.println(Qiniuyun.upFile("HOT-16H详情图.jpg", file2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
