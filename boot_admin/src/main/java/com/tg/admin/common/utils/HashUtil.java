package com.tg.admin.common.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECGenParameterSpec;

public class HashUtil {

    public static String getHash(){
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
            keyGen.initialize(ecSpec);
            KeyPair kp = keyGen.generateKeyPair();
            PrivateKey pvt = kp.getPrivate();
            ECPrivateKey epvt = (ECPrivateKey)pvt;
            String sepvt = adjustTo64(epvt.getS().toString(16)).toUpperCase();
            sepvt=sepvt.toLowerCase();
            return sepvt;
        }catch (Exception ex){
            return "";
        }
    }
    static private String adjustTo64(String s) {
        switch(s.length()) {
            case 62: return "00" + s;
            case 63: return "0" + s;
            case 64: return s;
            default:
                throw new IllegalArgumentException("not a valid key: " + s);
        }
    }

    public static void main(String[] args) throws Exception{
      String hash=  getHash();
        System.out.println(adjustTo64(hash));
    }
}
