package com.xiajianhx.demo.springcloud.netflix.client.service.impl;

import com.xiajianhx.demo.springcloud.netflix.client.bean.BaoSeckillGoods;
import com.xiajianhx.demo.springcloud.netflix.client.dao.BaoSeckillGoodsMapper;
import com.xiajianhx.demo.springcloud.netflix.client.service.SeckillService;
import com.xiajianhx.demo.springcloud.netflix.client.util.caches.CacheService;
import com.xiajianhx.demo.springcloud.netflix.client.vo.ResultTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private BaoSeckillGoodsMapper baoSeckillGoodsMapper;

    @Autowired
    private CacheService cacheService;

    private final String querySeckillRedisKeyPrefix = "seckill_request_";

    // redis实例 -- >库存详情
    // db实例 --> mysql
    // zookeeper实例 --> 执行时的分布式锁
    @Override
    public List<BaoSeckillGoods> querySeckillGoods(String mobile) {
        // 查询做频率限制
        String seckillKey = querySeckillRedisKeyPrefix + mobile;
        if (cacheService.exists(seckillKey)) {
            throw new RuntimeException("您的操作过于频繁，请稍后再试！！！");
        }
        cacheService.set(seckillKey, 0, 3L);
        return  baoSeckillGoodsMapper.selectAll();
    }

    @Override
    public ResultTemplate requestSeckill(String mobile, String goodsNo) {
        // // 1.查询缓存 【加锁】--> 防止缓存雪崩 -->加分布式锁
        // List<BaoSeckillGoods> goodsList =
        // JSON.parseArray(cacheService.get(SEC_GOODS_LIST_CACHE_NAME).toString(),
        // BaoSeckillGoods.class);
        // if (goodsList == null) {
        // Lock lock = new ZKDistributedLock(GlobalConfig.ZK_URL, "test1");
        // try {
        // lock.lock();
        // // 2.从数据库LOAD
        // cacheService.set(SEC_GOODS_LIST_CACHE_NAME,
        // JSON.toJSONString(goodsList));
        // } catch (Exception e) {
        // lock.unlock();
        // }
        // }
        // 放入消息队列中
        return null;
    }

    @Override
    public void querySeckillResult(String mobile) {

    }

    @Override
    public void execSeckill() {

    }

    // @Override
    // public void retrySeckill() {
    // throw new UnsupportedOperationException("暂不支持重试功能");
    // }
}
