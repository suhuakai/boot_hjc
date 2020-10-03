package com.tg.admin.common.utils;

import java.util.Random;

public class Utils {

    public static String getTxid() {
        StringBuffer txid = new StringBuffer();
        for (int i = 0; i < 64; i++) {
            int ii = new Random().nextInt(2);
            if (ii == 0) {
                txid.append(new Random().nextInt(9) + 1);
            } else {
                txid.append((char) (new Random().nextInt(5) + 97));
            }
        }
        return txid.toString();
    }
}
