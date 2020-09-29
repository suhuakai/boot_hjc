package com.tg.api.vo;

import com.tg.api.entity.UserEntity;
import lombok.Data;

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
}
