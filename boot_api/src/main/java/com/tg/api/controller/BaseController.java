package com.tg.api.controller;

import com.google.code.kaptcha.Producer;
import com.tg.api.common.constant.ConstantCache;
import com.tg.api.common.exception.RRException;
import com.tg.api.common.qiniuyun.Qiniuyun;
import com.tg.api.common.redis.RedisConfigService;
import com.tg.api.common.utils.Base64Util;
import com.tg.api.common.utils.R;
import com.tg.api.common.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author: Administrator tecsmile@outlook.com
 * @date: 2020/7/7
 * @description:
 */

@RestController
@RequestMapping("/user")
public class BaseController {

    @Autowired
    RedisConfigService redisConfigService;

    @Resource
    HttpServletRequest request;

    @Autowired
    Producer producer;

    @RequestMapping("/getUserId")
    public int getUserId() {
        String token = request.getHeader(ConstantCache.TOKEN);
        if (!redisConfigService.exists(token)) {
            throw new RRException("登入过期", ConstantCache.TOKENEXPIRATION);
        }
        Object userId = redisConfigService.get(token);
        return (Integer) userId;
    }

    @RequestMapping(value = "/img")
    public R captcha(String timestamp, String phone) throws IOException {
        Assert.isBlank(phone, "时间戳不能为空");
        Assert.isBlank(timestamp, "时间戳不能为空");
        //生成文字验证码
        String text = producer.createText();
        System.out.println(text);
        redisConfigService.set(phone + timestamp + ConstantCache.IMGTYPE, text, 1 * 60 * 3L);
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        Object base64Str = Base64Util.encode(baos.toByteArray());
        return R.ok(base64Str);
    }



    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public R uploadImg(@RequestParam("fileUpload") MultipartFile file) {
        try {
            return R.ok((Object) upload(file, file.getOriginalFilename()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }


    public String upload(MultipartFile file, String fileName) {
        String fileNameNow = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        try {
            InputStream inputStream = file.getInputStream();
            String key = UUID.randomUUID().toString() + fileNameNow;
            Qiniuyun.upInput(key, inputStream);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("上传异常，请联系管理员");
        }
    }


}

