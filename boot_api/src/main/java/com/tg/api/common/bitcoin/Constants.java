package com.tg.api.common.bitcoin;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;

/**
 * [描述]
 *
 * @author: zcy
 * @email: 1084294954@qq.com
 * @create: 2019-11-25
 **/
public class Constants {

    public static final boolean TEST = false;
    public static final NetworkParameters NETWORK_PARAMETERS = TEST ? TestNet3Params.get() : MainNetParams.get();
}
