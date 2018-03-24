package com.xiajianhx.demo.springcloud.netflix.client.config;

import com.xiajianhx.demo.springcloud.netflix.client.util.distributedlock.ZKDistributedLock;

import java.util.concurrent.locks.Lock;

public class GlobalConfig {
    // ZK 配置
    public static String ZK_URL = null;

    public static Lock getDistributedLock(String lockName) {
        return new ZKDistributedLock(GlobalConfig.ZK_URL, lockName);
    }

}
