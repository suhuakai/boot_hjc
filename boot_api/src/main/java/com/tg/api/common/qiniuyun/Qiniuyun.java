package com.tg.api.common.qiniuyun;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;

import java.io.InputStream;

public class Qiniuyun {

    private static String ACCESS_KEY = "R8F_6y9TDc1JELcSaUH0ofowg04SR9DKtNjunZ_v";

    private static String SECRET_KEY = "eFLJCSyZysNwmDKIAHK32mFQmvFc2O1RMT7dDky8";

    //cdn.szlzpt.com

    private static  Mac mac = new Mac(ACCESS_KEY,SECRET_KEY);
    private static PutPolicy putPolicy;
    private static PutExtra extra = new PutExtra();

    public static String upInput(String key, InputStream inputStream) throws Exception{
        String bucketName = "lvzhouyp123:"+key;
        putPolicy = new PutPolicy(bucketName);
        String uptoken = putPolicy.token(mac);
        PutRet ret = IoApi.Put(uptoken,key,inputStream,extra);
        System.out.println(ret.getResponse());
        return ret.getKey();
    }

}
