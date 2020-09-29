package com.tg.api.common.bitcoin;

import lombok.Data;

/**
 * [描述]
 *
 * @author: zcy
 * @email: 1084294954@qq.com
 * @create: 2019-11-25
 **/
@Data
public class BitWallet {

    /**
     * 助记词
     */
    private String mnemonic;
    /**
     * 助记词
     */
    private String address;
    /**
     * 助记词
     */
    private String privateKey;

    private String publicKey;

}
