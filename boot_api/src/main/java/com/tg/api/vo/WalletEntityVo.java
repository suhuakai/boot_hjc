package com.tg.api.vo;

import com.tg.api.entity.WalletEntity;
import lombok.Data;

@Data
public class WalletEntityVo extends WalletEntity {
    private String walletTypeName;

}
