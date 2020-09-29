package com.tg.api.common.utils;

import java.util.regex.Pattern;

/**
 * @Auther: zx
 * @Date: 18/05/05 16:46
 * @Description:
 */
public class RegularExpressionUtil {

    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@zuidaima.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }


    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }


    /**
     * 验证手机号码
     * （支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[3456789]\\d{9}$";
        return Pattern.matches(regex, mobile);
    }

    /**
     * 正则表达式验证密码
     * 必须包含字母和者数字，6-16位
     *
     * @param password
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPassword(String password) {
        // 6-20 位，字母、数字、字符
       /// String regStr = "^([A-Z]|[a-z]|[0-9]|[`-=[];,./~!@#$%^*()_+}{:?]){6,20}$";
        String regStr = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        return Pattern.matches(regStr, password);
    }

}
