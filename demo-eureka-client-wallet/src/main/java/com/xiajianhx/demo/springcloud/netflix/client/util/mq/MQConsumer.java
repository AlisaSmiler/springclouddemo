//package com.xiajianhx.demo.springcloud.netflix.client.util.mq;
//
//import com.alibaba.fastjson.JSON;
//import com.xiajianhx.demo.springcloud.netflix.client.bean.BaoSeckillResult;
//import com.xiajianhx.demo.springcloud.netflix.client.config.GlobalConfig;
//import com.xiajianhx.demo.springcloud.netflix.client.config.SeckillStausEnum;
//import com.xiajianhx.demo.springcloud.netflix.client.dao.BaoSeckillGoodsMapper;
//import com.xiajianhx.demo.springcloud.netflix.client.dao.BaoSeckillResultMapper;
//import com.xiajianhx.demo.springcloud.netflix.client.vo.UserSeckillVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//public class MQConsumer {
//
//    @Autowired
//    private BaoSeckillGoodsMapper baoSeckillGoodsMapper;
//    @Autowired
//    private BaoSeckillResultMapper baoSeckillResultMapper;
//
//    @Transactional
//    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
//    @JmsListener(destination = GlobalConfig.SECKILL_MQ_NAME)
//    public void receiveQueue(String text) {
//        System.out.println("Consumer收到的报文为:" + text);
//
//        UserSeckillVO vo = JSON.parseObject(text, UserSeckillVO.class);
//
//        int count = baoSeckillGoodsMapper.decreaseStockNum(vo.getGoodsNo());
//        System.out.println("db减库存操作,商品:" + vo.getGoodsNo() + "结果:" + count);
//
//        // 生成秒杀成功记录
//        BaoSeckillResult record = new BaoSeckillResult();
//        record.setMobile(vo.getMobile());
//        record.setGoodsNo(vo.getGoodsNo());
//        record.setStatus(SeckillStausEnum.SUCESS.getKey());
//        record.setCreateTime(System.currentTimeMillis());
//        count = baoSeckillResultMapper.insertSelective(record);
//        System.out.println("db生成秒杀记录,用户:" + vo.getMobile() + "商品:" + vo.getGoodsNo() + "结果:" + count);
//    }
//}
