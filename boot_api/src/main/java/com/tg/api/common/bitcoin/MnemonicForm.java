package com.tg.api.common.bitcoin;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * [助记词导入的参数表]
 *
 * @author: zcy
 * @email: 1084294954@qq.com
 * @create: 2019-11-25
 **/

@Data
public class MnemonicForm {


    @NotBlank(message = "助记词不能为空")
    private String mnemonic;

    private String body;
}
