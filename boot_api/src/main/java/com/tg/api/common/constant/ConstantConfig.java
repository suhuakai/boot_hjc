package com.tg.api.common.constant;


public class ConstantConfig {

    public final static String HTTP_SESSION_ID = "JSESSIONID";
    /**
     * 用户对象
     */
    public final static String SESSION_USER_INFO = "sessionUserInfo";

    public  static Integer getUUID(){
        Integer random =0 ;
        for(int j = 0; j< 100; j++){
            random =(int)((Math.random()*9+1)*100000);
        }
        return  random;
    }

    /**
     * 钱包类型
     */
    public enum WalletType {
        goldPool(1, "金矿池"),
        silverPool(2, "银矿池"),
        contributionPool(3, "贡献池")
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
