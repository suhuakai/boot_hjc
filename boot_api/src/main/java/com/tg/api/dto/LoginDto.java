package com.tg.api.dto;

import lombok.Data;

/**
 * @author: Administrator tecsmile@outlook.com
 * @date: 2020/7/8
 * @description:
 */
@Data
public class LoginDto {


    private String name;

    private String password;

    private String  newPassword;

    private String code;

    private String dealPassword;

    private String identityCard;

    private String timestamp;

    private String type;
}
