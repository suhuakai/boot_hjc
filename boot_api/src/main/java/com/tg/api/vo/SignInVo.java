package com.tg.api.vo;

import com.tg.api.entity.SigninEntity;
import lombok.Data;

@Data
public class SignInVo extends SigninEntity {
    private  String signTypeName;
    private String walletTypeName;
}
