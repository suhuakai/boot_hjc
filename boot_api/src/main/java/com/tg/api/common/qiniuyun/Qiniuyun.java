package com.tg.api.common.qiniuyun;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.tg.api.common.config.JacksonConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.PropertySource;

import javax.xml.bind.ValidationException;
import java.io.FileInputStream;

@PropertySource("classpath:qiniu_client.properties")
@Log4j2
public class Qiniuyun {

    static String accessKey = null;
    static String secretKey = null;
    static String bucket = null;


    public static void init() throws Exception {
        JacksonConfig.init("classpath:qiniu_client.properties");
        accessKey = JacksonConfig.getProperty("accessKey");
        secretKey = JacksonConfig.getProperty("secretKey");
        bucket = JacksonConfig.getProperty("bucket");
    }

    /**
     * 上传文件
     *
     * @param fis      文件流
     * @param fileName 文件名称
     * @return
     * @throws Exception
     */
    public static String upload(FileInputStream fis, String fileName) throws Exception {
        init();
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);

        if (auth == null) {
            throw new ValidationException("密钥错误");
        }

        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(fis, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("putRet.key:" + putRet.key);
            log.info("putRet.hash:" + putRet.hash);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.out.println(r.toString());
            try {
                System.out.println(r.bodyString());
            } catch (QiniuException ex2) {
                System.out.println(ex2.getMessage());
            }
            return null;
        }
    }

}
