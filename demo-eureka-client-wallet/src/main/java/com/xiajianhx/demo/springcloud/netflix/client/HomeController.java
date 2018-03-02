package com.xiajianhx.demo.springcloud.netflix.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableEurekaClient
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @ResponseBody
    @RequestMapping("/home2")
    public String hello2() {
        return "Hello wallet.api!";
    }

    @ResponseBody
    @RequestMapping("/user/wallet/{userId}")
    public int getUsableWallet(@PathVariable String userId) {
        int balance = 1000;
        System.out.println();
        LOGGER.info("钱包接口 用户{}的钱包余额={}", userId, balance);
        return balance;
    }
}
