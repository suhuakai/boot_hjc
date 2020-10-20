package com.tg.api.vo;

import com.tg.api.entity.UserEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserVo extends UserEntity {
     private String token;
     /**
      *确认密码
      */
     private String  confirmPwd;
     /**
      * 确认交易密码
      */
     private String confirmDealPwd;
     private String address;
     private  String vipLevel;
     private BigDecimal earnings;
     private BigDecimal earnMoney ;

     @Override
     public String toString() {
          return "UserVo{" +
                  "token='" + token + '\'' +
                  ", confirmPwd='" + confirmPwd + '\'' +
                  ", confirmDealPwd='" + confirmDealPwd + '\'' +
                  ", address='" + address + '\'' +
                  ", vipLevel='" + vipLevel + '\'' +
                  ", earnings=" + earnings +
                  ", earnMoney=" + earnMoney +
                  '}';
     }
}
