package com.xiajianhx.demo.springcloud.netflix.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.xiajianhx.demo.springcloud.netflix.client.bean.BaoSeckillGoods;
import com.xiajianhx.demo.springcloud.netflix.client.bean.BaoSeckillResult;
import com.xiajianhx.demo.springcloud.netflix.client.config.GlobalConfig;
import com.xiajianhx.demo.springcloud.netflix.client.config.SeckillStausEnum;
import com.xiajianhx.demo.springcloud.netflix.client.dao.BaoSeckillGoodsMapper;
import com.xiajianhx.demo.springcloud.netflix.client.dao.BaoSeckillResultMapper;
import com.xiajianhx.demo.springcloud.netflix.client.service.SeckillService;
import com.xiajianhx.demo.springcloud.netflix.client.util.caches.MyCacheService;
import com.xiajianhx.demo.springcloud.netflix.client.vo.ResultTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * 初版不做消息队列，直接同步返回，用分布式锁就好 redis实例 -- >库存详情 db实例 --> mysql zookeeper实例 -->
 * 执行时的分布式锁
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private static final String salt = "sdjfysodyur23872-348B%^^&&%$78";

    // 用户秒杀记录 防止重复秒杀
    private static final String USER_SECKILL_RECORD_PREFIX = "user_seckill_record_";
    // 库存数量
    private static final String SECKILL_STOCK_NUM_PREFIX = "stock_";
    // 商品记录
    private static final String SECKILL_GOODS_LIST = "seckill_goods";

    @Autowired
    private MyCacheService myCacheService;
//    @Autowired
//    private MQProducer mqProducer;

    @Autowired
    private BaoSeckillGoodsMapper baoSeckillGoodsMapper;
    @Autowired
    private BaoSeckillResultMapper baoSeckillResultMapper;

    @Override
    public List<BaoSeckillGoods> querySeckillGoods(String mobile) {
        // 输入load入缓存
        if (!myCacheService.exists(SECKILL_GOODS_LIST)) {
            List<BaoSeckillGoods> records = baoSeckillGoodsMapper.selectAll();
            for (BaoSeckillGoods record : records) {
                myCacheService.hmSet(SECKILL_GOODS_LIST, record.getGoodsNo(), JSON.toJSONString(record));
            }
        }

        List<BaoSeckillGoods> res = new ArrayList<>();

        // 库存从Redis中获取
        for (BaoSeckillGoods goods : JSON.parseArray(myCacheService.hmGetValues(SECKILL_GOODS_LIST).toString(), BaoSeckillGoods.class)) {
            Object newstStockNum = myCacheService.get(SECKILL_STOCK_NUM_PREFIX + goods.getGoodsNo());
            goods.setStockNum(Integer.valueOf(newstStockNum.toString()));
            res.add(goods);
        }

        return res;
    }

    @Override
    public String generateSeckillToken(String mobile, String goodsNo) {
        String plainText = mobile + goodsNo + salt;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 后端生成的加密串放入前端隐藏域中，到时候再自动提交过来
            byte[] md5Bytes = md.digest(plainText.getBytes());
            return new String(md5Bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("token生成失败");
        }
    }

    // 是否需要分布式锁--最后再套，先做单机
    // 秒杀商品是否存在
    // 商品是否在秒杀时间内
    // 商品库存是否够
    // 用户是否重复秒杀
    // 设置布隆过滤器
    // 放入消息队列中
    @Transactional
    @Override
    public ResultTemplate requestSeckill(String mobile, String goodsNo, String token) {
        // 签名校验--后端为每个商品生成相应的签名md5，然后前端传入
        if (!generateSeckillToken(mobile, goodsNo).equals(token)) {
            throw new RuntimeException("臭不要脸，作弊！！！");
        }

//        if (!myCacheService.exists(goodsNo)) {
//        }

        String goodsStr = (String) myCacheService.hmGet(SECKILL_GOODS_LIST, goodsNo);
        if (StringUtils.isBlank(goodsStr)) {
            throw new RuntimeException("商品不存在");
        }

        BaoSeckillGoods goods = JSON.parseObject(goodsStr, BaoSeckillGoods.class);

        if (goods.getStartTime() > System.currentTimeMillis() || goods.getEndTime() < System.currentTimeMillis()) {
            return ResultTemplate.fail("商品秒杀已结束");
        }

        // 分布式锁 减库存，生成秒杀记录
        Lock lock = GlobalConfig.getDistributedLock("test1");
        lock.lock();
        try {
            // 库存从cache中获取
            String seckillRecordKey = USER_SECKILL_RECORD_PREFIX + goodsNo + "_" + mobile;
            if (myCacheService.get(seckillRecordKey) != null) {
                throw new RuntimeException("重复秒杀");
            }

            // 查看库存
            String stockKey = getStockKey(goodsNo);
            long stockNum = Long.valueOf(myCacheService.get(stockKey).toString());
            if (stockNum <= 0) {
                return ResultTemplate.fail("手慢啦，商品已被抢完");
            }

            // 库存 -1
            myCacheService.inscrease(stockKey, -1);

            // 交给mq处理
//            mqProducer.sendMessage(GlobalConfig.SECKILL_MQ_NAME, JSON.toJSONString(new UserSeckillVO(goodsNo, mobile)));

            int count = baoSeckillGoodsMapper.decreaseStockNum(goodsNo);
            System.out.println("db减库存操作,商品:" + goodsNo + "结果:" + count);

            // 生成秒杀成功记录
            BaoSeckillResult record = new BaoSeckillResult();
            record.setMobile(mobile);
            record.setGoodsNo(goodsNo);
            record.setStatus(SeckillStausEnum.SUCESS.getKey());
            record.setCreateTime(System.currentTimeMillis());
            count = baoSeckillResultMapper.insertSelective(record);
            System.out.println("db生成秒杀记录,用户:" + mobile + "商品:" + goodsNo + "结果:" + count);

            // 置已经秒杀成功过
            myCacheService.set(seckillRecordKey, "1");
            return ResultTemplate.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("秒杀失败，请稍后再试");
        } finally {
            lock.unlock();
        }
    }

    private String getStockKey(String goodsNo) {
        return SECKILL_STOCK_NUM_PREFIX + goodsNo;
    }

    @Override
    public ResultTemplate querySeckillResult(String mobile) {
        BaoSeckillResult record = new BaoSeckillResult();
        record.setMobile(mobile);
        return ResultTemplate.success(baoSeckillResultMapper.select(record));
    }

    // @Override
    // public void retrySeckill() {
    // throw new UnsupportedOperationException("暂不支持重试功能");
    // }
}
