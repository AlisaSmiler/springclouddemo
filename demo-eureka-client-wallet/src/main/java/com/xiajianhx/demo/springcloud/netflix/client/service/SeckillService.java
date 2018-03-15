package com.xiajianhx.demo.springcloud.netflix.client.service;

import com.xiajianhx.demo.springcloud.netflix.client.vo.GoodsVO;

import java.util.List;

public interface SeckillService {

    /**
     * 秒杀商品列表
     * 
     * @return
     */
    List<GoodsVO> querySeckillGoods();

    /**
     * 用户请求秒杀【手机作为主键】
     */
    void requestSeckill(String mobile, String goodsNo);

    /**
     * 用户查询秒杀结果【浏览器不做推送】
     */
    void querySeckillResult(String mobile, String goodsNo);

    /**
     * 执行秒杀【做快速失败】
     */
    void execSeckill();

    /**
     * 失败执行秒杀【重试先不做，如果是支付类型，建议重试，并且保证幂等性】
     */
    void retrySeckill();
}
