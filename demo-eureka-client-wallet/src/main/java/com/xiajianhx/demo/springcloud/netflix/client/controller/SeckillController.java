package com.xiajianhx.demo.springcloud.netflix.client.controller;

import com.xiajianhx.demo.springcloud.netflix.client.vo.ResultTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by jiaxiangbo
 * @date 2017/12/19
 */
@RestController
@RequestMapping(value = "/seckill")
public class SeckillController  {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeckillController.class);

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResultTemplate saveAgentInfo() {
        return null;
    }
}
