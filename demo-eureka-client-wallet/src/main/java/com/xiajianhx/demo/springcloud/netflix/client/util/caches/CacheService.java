package com.xiajianhx.demo.springcloud.netflix.client.util.caches;

import java.util.List;
import java.util.Set;

public interface CacheService {
    boolean set(final String key, Object value);

    boolean set(final String key, Object value, Long expireTime);

    void remove(boolean isPipeline, final String... keys);

    void removePattern(final String pattern);

    void remove(final String key);

    boolean exists(final String key);

    Object get(final String key);

    void add(String key, Object value);

    Set<Object> setMembers(String key);

    void zAdd(String key, Object value, double source);

    Set<Object> rangeByScore(String key, double scoure, double scoure1);

    void hmSet(String key, Object hashKey, Object value);

    Object hmGet(String key, Object hashKey);

    void lPush(String k, Object v);

    void rPush(String k, Object v);

    List<Object> lRange(String k, long l, long l1);

    void sAdd(String key, Object value);

    void sRemove(String key, Object value);

    /**
     * Redis自增操作
     * @param key
     * @param value
     * @return
     */
    long inscrease(final String key, long value);

}
