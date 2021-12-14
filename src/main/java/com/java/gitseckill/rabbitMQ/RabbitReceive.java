package com.java.gitseckill.rabbitMQ;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.java.gitseckill.entity.*;
import com.java.gitseckill.execption.GlobalException;
import com.java.gitseckill.service.impl.*;
import com.java.gitseckill.utils.Const;
import com.java.gitseckill.utils.RedisUtils;
import com.java.gitseckill.vo.Result;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author javaercdy
 * @create 2021-12-03$-{TIME}
 */
@Slf4j
@Component
public class RabbitReceive {
    @Autowired
    OrderInfoServiceImpl orderInfoService;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    GoodsServiceImpl goodsService;
    @Autowired
    GoodsSeckillServiceImpl goodsSeckillService;
    @Autowired
    RedisUtils redisUtils;

    private final static String QUE_FIRST="QUE_FIRST";

    @RabbitListener(bindings = {
            @QueueBinding(
                    value =@Queue("ORDER_QUE1"),
                    exchange = @Exchange(name ="order_Exchange",type = "topic"),
                    key = {"order"}
            )
    })
    public void orderReceive(String message){
        log.info("ORDER_QUE1,开始生成订单");
        SeckillMessage seckillMessage = JSONUtil.toBean(message,SeckillMessage.class);
        if (seckillMessage==null){
            throw new GlobalException(Const.RABBITMQ_ERROR);
        }
        if (seckillMessage.getGoodsId()==null||seckillMessage.getUser()==null){
            throw new GlobalException(Const.RABBITMQ_ERROR);
        }
        if (redisUtils.hasKey("goods:"+seckillMessage.getGoodsId(),seckillMessage.getUser().getId())){
            throw new GlobalException(Const.RABBITMQ_ERROR);
        }
            orderService.secKill(seckillMessage.getUser(), seckillMessage.getGoodsId());
            log.info("ORDER_QUE1,订单生成完成!");
    }

//    @RabbitListener(bindings = {
//            @QueueBinding(
//                    value=@Queue(value = "ORDER_QUE2",
//                    arguments = {@Argument(name="x-dead-letter-exchange",value = "dead-exchange"),
//                                 @Argument(name="x-dead-letter-routing-key",value = "deadkey"),
//                                @Argument(name="x-message-ttl",value = "10000",type = "java.lang.Integer")}),
//                    exchange = @Exchange(name = "order_Exchange2",type = "topic"),
//                    key = {"*order"}
//            )
//    })
//    public void orderReceive2(Message message, Channel channel) throws IOException {
//        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//        channel.basicNack(deliveryTag,false,false);
//        System.out.println("我都拒绝了,应该没有我了,有我说明你,拒绝了仍然消费这个消息");
//    }
//    @RabbitListener(bindings = {
//            @QueueBinding(
//                    value=@Queue(value = "dead_quet"),
//                    exchange = @Exchange(name = "dead-exchange",type = "direct"),
//                    key = {"deadkey"}
//            )
//    })
//    public void deadReceive3(Message message,Channel channel){
//        System.out.println("到死信了");
//    }
}


























