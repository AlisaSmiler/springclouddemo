package com.xiajianhx.demo.springcloud.netflix.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@EnableEurekaClient
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    RestTemplate restTemplate;

    @ResponseBody
    @RequestMapping("/home1")
    public String hello() {
        return "Hello checkout !";
    }

    @ResponseBody
    @GetMapping("/user/checkout/{userId}")
    public String checkout(@PathVariable String userId) {
        int walletBalance = restTemplate.getForObject("http://service-wallet/user/wallet/" + userId, Integer.class);
        LOGGER.info("checkout调用wallet 用户{}钱包余额={}", userId, walletBalance);
        return String.format("用户%s钱包余额=%d", userId, walletBalance);
    }

}
