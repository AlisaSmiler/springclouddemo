package com.xiajianhx.demo.springcloud.netflix.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <pre>
 * 	 <b>系统启动加载</b>
 * </pre>
 *
 * @author lisa
 */
@Configuration
@EnableScheduling
public class ApplicationStartUpListener implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${myconfig.zookeeper.url}")
    private String zkUrl;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 加载配置文件中内容到本身中-->从配置中心拿
        GlobalConfig.ZK_URL = zkUrl;

    }
}