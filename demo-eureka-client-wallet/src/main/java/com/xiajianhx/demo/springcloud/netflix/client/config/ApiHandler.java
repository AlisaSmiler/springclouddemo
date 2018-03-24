package com.xiajianhx.demo.springcloud.netflix.client.config;

import com.xiajianhx.demo.springcloud.netflix.client.util.caches.CacheService;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
public class ApiHandler {
    // 用户秒杀请求
    private static final String USER_SECKILL_REQUEST_PREFIX = "seckill_request_";

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private CacheService cacheService;

    private ThreadLocal<Long> startTime = new ThreadLocal<>();
    private ThreadLocal<String> requestMobile = new ThreadLocal<>();

    @Pointcut("execution( * com.xiajianhx.demo.springcloud.netflix.client.controller.*..*.*(..)) ")
    public void web() {
    }

    @Before("web()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuilder builder = new StringBuilder();

        builder.append("HTTP METHOD = ").append(request.getMethod()).append("\r\n\t\t");
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        builder.append("CLASS_METHOD = ").append(classMethod).append("\r\n\t\t");

        Map<String, String[]> requestParams = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            StringBuilder value = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                value.append(values[i]).append((i == values.length - 1) ? "" : ",");
            }

            // 操作频度检验
            if (name.equals("mobile")) {
                checkUserFrequency(value.toString());
            }

            builder.append("Parameter: ").append(name).append(" = ").append(value).append("\r\n\t\t");
        }
        builder.append("requestBody : ").append("\r\n\t\t");
        for (Object object : joinPoint.getArgs()) {
            builder.append("Args: ").append(object).append("\r\n\t\t");
        }
        logger.info("\r\n\t\t↓↓↓↓↓↓↓↓↓↓ 消息头内容 ↓↓↓↓↓↓↓↓↓↓\r\n\t\t"
                + builder + "↑↑↑↑↑↑↑↑↑↑ 消息头内容 ↑↑↑↑↑↑↑↑↑↑");

    }

    @AfterReturning(returning = "ret", pointcut = "web()")
    public void doAfterReturning(Object ret) throws Throwable {
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
        // 记录用户请求，防止请求频率过高
        cacheService.set(requestMobile.get(), 0, 1000L);
    }

    private void checkUserFrequency(String mobileNumber) {
        String requestKey = USER_SECKILL_REQUEST_PREFIX + mobileNumber;
        requestMobile.set(requestKey);
        if (cacheService.exists(requestKey)) {
            throw new RuntimeException("你不累嘛，歇会！！！");
        }
    }
}
