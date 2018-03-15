package com.xiajianhx.demo.springcloud.netflix.client.service.impl;

import com.xiajianhx.demo.springcloud.netflix.client.service.SeckillService;
import com.xiajianhx.demo.springcloud.netflix.client.vo.GoodsVO;

import java.util.List;

public class SeckillServiceImpl implements SeckillService {
    // redis实例
    // mq实例
    // db实例
    // zookeeper实例
    @Override
    public List<GoodsVO> querySeckillGoods() {

        return null;
    }

    @Override
    public void requestSeckill(String mobile, String goodsNo) {

    }

    @Override
    public void querySeckillResult(String mobile, String goodsNo) {

    }

    @Override
    public void execSeckill() {

    }

    @Override
    public void retrySeckill() {

    }
}
