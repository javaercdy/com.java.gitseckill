package com.java.gitseckill.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author javaercdy
 * @create 2021-12-07$-{TIME}
 */
//@Configuration
public class MQconfig {
    private final static String QUE_FIRST="QUE_FIRST";
    private final static String EX_TOPIC="EX_TOPIC";


    @Bean(QUE_FIRST)
    public Queue queue1(){
        return QueueBuilder.durable(QUE_FIRST).build();
    }

    @Bean(EX_TOPIC)
    public TopicExchange exchange1(){
        return  new TopicExchange(EX_TOPIC);
    }

    @Bean
    public Binding Q1BingE1(@Qualifier(QUE_FIRST)Queue queue,@Qualifier(EX_TOPIC)TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("*.order");
    }

}
