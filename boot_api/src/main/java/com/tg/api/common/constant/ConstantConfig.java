package com.tg.api.common.constant;


import java.util.Random;

public class ConstantConfig {

    public final static String HTTP_SESSION_ID = "JSESSIONID";

    public  static int getUUID(){
        int[] array = {0,1,2,3,4,5,6,7,8,9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for(int i = 0; i < 6; i++){
            result = array[i];
            System.out.print(array[i]);
        }
        System.out.print("----"+result);
        return  result;
    }

    public static void main(String[] args){
       getUUID();
    }

    /**
     * 钱包类型
     */
    public enum WalletType {
        balance(1,"余额"),
        platformCoin(2,"金券"),
        goldPool(3, "金矿池"),
        silverPool(4, "银矿池"),
        contributionPool(5, "算力池"),
        optionShare(6, "期股权");

        ;

        private Integer type;

        private String typeName;

        public Integer getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        WalletType(Integer type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }
    }

}
