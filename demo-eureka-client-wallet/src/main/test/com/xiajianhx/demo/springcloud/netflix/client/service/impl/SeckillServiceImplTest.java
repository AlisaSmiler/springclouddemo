package com.xiajianhx.demo.springcloud.netflix.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.xiajianhx.demo.springcloud.netflix.client.bean.BaoSeckillGoods;
import com.xiajianhx.demo.springcloud.netflix.client.service.SeckillService;
import com.xiajianhx.demo.springcloud.netflix.client.vo.ResultTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by alisa on 2018/3/27.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SeckillServiceImplTest {

    @Autowired
    private SeckillService seckillService;

    @Test
    public void querySeckillGoods() throws Exception {
        List<BaoSeckillGoods> baoSeckillGoods = seckillService.querySeckillGoods("13632450924");

        System.out.println("============" + JSON.toJSONString(baoSeckillGoods));
    }

    @Test
    public void generateSeckillToken() throws Exception {
    }

    @Test
    public void requestSeckill() throws Exception {
        CountDownLatch begin = new CountDownLatch(1);
        int threadNum = 500;
        CountDownLatch end = new CountDownLatch(threadNum);
        System.out.println("=============begin==============");

        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        begin.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String mobile = Thread.currentThread().getName();
                    String goodsNo = "22222";
                    String token = seckillService.generateSeckillToken(mobile, goodsNo);

                    try {
                        ResultTemplate res = seckillService.requestSeckill(mobile, goodsNo, token);
                        System.out.println("秒杀结果：======="+JSON.toJSONString(res));
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("异常信息==" + e.getMessage());
                    }
                    end.countDown();
                }
            }, "Mo1" + i);
            thread.setDaemon(true);
            thread.start();
        }
        begin.countDown();
        end.await();
        System.out.println("=============end==============");

    }

    @Test
    public void querySeckillResult() throws Exception {
        ResultTemplate res = seckillService.querySeckillResult("13632450924");
        System.out.println(JSON.toJSONString(res));

    }

}