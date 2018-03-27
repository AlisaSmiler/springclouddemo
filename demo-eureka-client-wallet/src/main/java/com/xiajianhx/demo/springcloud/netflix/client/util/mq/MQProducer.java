//package com.xiajianhx.demo.springcloud.netflix.client.util.mq;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MQProducer {
//    @Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
//    private JmsTemplate jmsTemplate;
//
//    // 发送消息，destination是发送到的队列，message是待发送的消息
//    public void sendMessage(String destination, final String message) {
//        jmsTemplate.convertAndSend(destination, message);
//    }
//}
