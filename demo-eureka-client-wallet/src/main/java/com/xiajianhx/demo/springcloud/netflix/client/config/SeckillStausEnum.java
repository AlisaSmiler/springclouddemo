package com.xiajianhx.demo.springcloud.netflix.client.config;

public enum SeckillStausEnum {
    SUCESS(10 , "秒杀成功"),
    FAIL(20, "秒杀失败"),
    ;

    byte key;
    String val; // 超时时间，毫秒为单位

    SeckillStausEnum(int key, String val) {
        this.key = (byte) key;
        this.val = val;
    }

    public byte getKey() {
        return key;
    }

    public String getVal() {
        return val;
    }
}
