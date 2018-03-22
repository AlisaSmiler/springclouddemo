package com.xiajianhx.demo.springcloud.netflix.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.xiajianhx.demo.springcloud.netflix.client.bean.BaoSeckillGoods;
import com.xiajianhx.demo.springcloud.netflix.client.config.SeckillKeyEnum;
import com.xiajianhx.demo.springcloud.netflix.client.dao.BaoSeckillGoodsMapper;
import com.xiajianhx.demo.springcloud.netflix.client.service.SeckillService;
import com.xiajianhx.demo.springcloud.netflix.client.util.caches.CacheService;
import com.xiajianhx.demo.springcloud.netflix.client.util.mq.MQProducer;
import com.xiajianhx.demo.springcloud.netflix.client.vo.ResultTemplate;
import com.xiajianhx.demo.springcloud.netflix.client.vo.UserSeckillVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private BaoSeckillGoodsMapper baoSeckillGoodsMapper;

    @Autowired
    private CacheService cacheService;
    @Autowired
    private MQProducer mqProducer;

    private static final String USER_SECKILL_RECORD_PREFIX = "user_seckill_record_";


    // redis实例 -- >库存详情
    // db实例 --> mysql
    // zookeeper实例 --> 执行时的分布式锁
    @Override
    public List<BaoSeckillGoods> querySeckillGoods(String mobile) {
        // 查询做频率限制
        String seckillKey = SeckillKeyEnum.query.getPrefix() + mobile;
        if (cacheService.exists(seckillKey)) {
            throw new RuntimeException("您的操作过于频繁，请稍后再试！！！");
        }
        cacheService.set(seckillKey, 0, SeckillKeyEnum.query.getTimeout());
        return  baoSeckillGoodsMapper.selectAll();
    }

    @Override
    public ResultTemplate exposeSeckillUrl(String mobile, String goodsNo) {
        return null;
    }

    @Override
    public ResultTemplate requestSeckill(String mobile, String goodsNo, String sign) {
        // 签名校验--后端为每个商品生成相应的签名md5某某某，然后前端传入

        // 查询做频率限制
        String seckillKey = SeckillKeyEnum.request.getPrefix() + mobile;
        if (cacheService.exists(seckillKey)) {
            throw new RuntimeException("你不累嘛，歇会！！！");
        }

        // 是否需要分布式锁--最后再套，先做单机
        // 秒杀商品是否存在
        // 商品是否在秒杀时间内
        // 商品库存是否够
        // 用户是否重复秒杀
        // 设置布隆过滤器
        // 放入消息队列中
        if (!cacheService.exists(goodsNo)){
            throw new RuntimeException("商品不存在");
        }
        BaoSeckillGoods goods = JSON.parseObject((String)cacheService.get(goodsNo), BaoSeckillGoods.class);
        if (goods.getStartTime() > System.currentTimeMillis()
                || goods.getEndTime() < System.currentTimeMillis()) {
            return ResultTemplate.fail("商品秒杀已结束");
        }
        if (goods.getStockNum() <= 0) {
            return ResultTemplate.fail("手慢啦，商品已被抢完");
        }
        String seckillRecordKey = USER_SECKILL_RECORD_PREFIX + goodsNo + "_" + mobile;
        if (cacheService.get(seckillRecordKey) != null) {
            throw new RuntimeException("重复秒杀");
        }

        // 减库存
        goods.setStockNum(goods.getStockNum() - 1);
        cacheService.set(goodsNo, JSON.toJSONString(goods));

        UserSeckillVO seckillVO = new UserSeckillVO(goodsNo, mobile);
        // TODO 队列是否需要对商品编号进行限制 -- 分成不同的队列
        mqProducer.sendMessage("seckill_goods", JSON.toJSONString(seckillVO));
        cacheService.set(seckillKey, 1, SeckillKeyEnum.request.getTimeout());
        cacheService.set(seckillRecordKey, 1);
        return ResultTemplate.success();
    }

    @Override
    public void querySeckillResult(String mobile) {
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
    }

    @Override
    public void execSeckill() {
        // // 1.查询缓存 【加锁】--> 防止缓存雪崩 -->加分布式锁
        // 从消息队列中获取订单
        // 执行秒杀逻辑 减商品库存 增加秒杀记录
        // 设置数据库乐观锁
    }

    // @Override
    // public void retrySeckill() {
    // throw new UnsupportedOperationException("暂不支持重试功能");
    // }
}
