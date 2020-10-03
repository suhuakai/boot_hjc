package com.tg.api.common.bitcoin;


import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * [描述]
 *
 * @author: zcy
 * @email: 1084294954@qq.com
 * @create: 2019-11-25
 **/
public class BitcoinWalletUtils {


    public static BitWallet createWallet(){
        Wallet wallet = new Wallet(Constants.NETWORK_PARAMETERS);
        BitWallet bitWallet = new BitWallet();
        bitWallet.setMnemonic(mnemonicToString(wallet.getKeyChainSeed().getMnemonicCode()));
        bitWallet.setAddress(wallet.currentReceiveAddress().toString());
        bitWallet.setPrivateKey(wallet.currentReceiveKey().getPrivateKeyAsWiF(Constants.NETWORK_PARAMETERS));
        return bitWallet;
    }

    public static BitWallet importWalletByMnemonic(String mnemonic){
        DeterministicSeed seed = new DeterministicSeed(Arrays.asList(mnemonic.split(" ")), null, "", 0L);
        Wallet wallet = Wallet.fromSeed(Constants.NETWORK_PARAMETERS, seed);
        BitWallet bitWallet = new BitWallet();
        bitWallet.setMnemonic(mnemonic);
        bitWallet.setPrivateKey(wallet.currentReceiveKey().getPrivateKeyAsWiF(Constants.NETWORK_PARAMETERS));
        bitWallet.setAddress(wallet.currentReceiveAddress().toString());
        return bitWallet;
    }

    public static BitWallet importWalletByPrivateKey(String privateKey){
        ECKey ecKey = DumpedPrivateKey.fromBase58(Constants.NETWORK_PARAMETERS, privateKey).getKey();//可以解析出来ECKEY 信息包含地址
        BitWallet bitWallet = new BitWallet();
        bitWallet.setAddress(ecKey.toAddress(Constants.NETWORK_PARAMETERS).toString());
        bitWallet.setPrivateKey(privateKey);
        return bitWallet;
    }


    private static String mnemonicToString(List<String> mnemonicCode){
        if (null != mnemonicCode && !mnemonicCode.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (String mnemonic : mnemonicCode){
                sb.append(mnemonic).append(" ");
            }
            return sb.toString().trim();
        }
        return null;
    }

    public static void main(String[] args){
       System.out.println(new BigDecimal("100").divide(new BigDecimal("5095363"),10,BigDecimal.ROUND_DOWN));
    }

}
