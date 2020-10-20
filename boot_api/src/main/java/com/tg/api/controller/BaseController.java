package com.tg.api.controller;

import com.google.code.kaptcha.Producer;
import com.tg.api.common.constant.ConstantCache;
import com.tg.api.common.exception.RRException;
import com.tg.api.common.qiniuyun.Qiniuyun;
import com.tg.api.common.redis.RedisConfigService;
import com.tg.api.common.utils.BASE64DecodedMultipartFile;
import com.tg.api.common.utils.Base64Util;
import com.tg.api.common.utils.R;
import com.tg.api.common.validator.Assert;
import com.tg.api.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        System.out.println(userId);
        return Integer.valueOf(userId+"");
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
    public R uploadImg(@RequestParam(value = "file") MultipartFile file) throws Exception {
        String filepath = file.getOriginalFilename();
        String fileName = filepath.substring(filepath.lastIndexOf("/") + 1, filepath.lastIndexOf("."));
        String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());

        FileInputStream inputStream = (FileInputStream) file.getInputStream();

        //获取当前时间
        String now = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
        fileName = fileName + "(" + now + ")" + "." + fileType;
        //文件位置
        String fileId = Qiniuyun.upInput(fileName, inputStream);
        return R.ok(fileId);
    }

    /**
     * base64
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public R upload(String file) throws Exception {
        String[] baseStrs = file.split(",");
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b;
        b = decoder.decodeBuffer(baseStrs[1]);

        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        MultipartFile multipartFile = new BASE64DecodedMultipartFile(b, baseStrs[0]);

        String filepath = multipartFile.getOriginalFilename();
        String fileName = filepath.substring(filepath.lastIndexOf("/") + 1, filepath.lastIndexOf("."));
        String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());

        InputStream inputStream = multipartFile.getInputStream();

        //获取当前时间
        String now = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
        fileName = fileName + "(" + now + ")" + "." + fileType;
        //文件位置
        String fileId = Qiniuyun.upInput(fileName, inputStream);
        return R.ok((Object) fileId);
    }


    public String upload(MultipartFile file, String fileName) {
        String fileNameNow = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        try {
            InputStream inputStream = file.getInputStream();
            String key = UUID.randomUUID().toString() + fileNameNow;
            //  Qiniuyun.(key, fileName);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("上传异常，请联系管理员");
        }
    }


}

