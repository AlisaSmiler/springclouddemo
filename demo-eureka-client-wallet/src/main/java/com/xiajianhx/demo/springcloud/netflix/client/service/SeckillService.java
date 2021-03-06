package com.xiajianhx.demo.springcloud.netflix.client.service;

import com.xiajianhx.demo.springcloud.netflix.client.bean.BaoSeckillGoods;
import com.xiajianhx.demo.springcloud.netflix.client.vo.ResultTemplate;

import java.util.List;

public interface SeckillService {

    /**
     * 秒杀商品列表
     * 
     * @return
     * @param mobile
     */
    List<BaoSeckillGoods> querySeckillGoods(String mobile);

    String generateSeckillToken(String mobile, String goodsNo);

    /**
     * 用户请求秒杀【手机作为主键】
     */
    ResultTemplate requestSeckill(String mobile, String goodsNo, String token);

    /**
     * 用户查询秒杀结果【浏览器不做推送】
     */
    ResultTemplate querySeckillResult(String mobile);

//    /**
//     * 失败执行秒杀【重试先不做，如果是支付类型，建议重试，并且保证幂等性】
//     */
//    void retrySeckill();
}
