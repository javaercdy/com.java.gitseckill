package com.java.gitseckill.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.java.gitseckill.entity.*;
import com.java.gitseckill.execption.GlobalException;
import com.java.gitseckill.service.impl.GoodsSeckillServiceImpl;
import com.java.gitseckill.service.impl.OrderServiceImpl;
import com.java.gitseckill.utils.Const;
import com.java.gitseckill.utils.RedisUtils;
import com.java.gitseckill.vo.GoodsVo;
import com.java.gitseckill.vo.Result;
import com.java.gitseckill.vo.SysUserVo;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cdy
 * @since 2021-11-30
 */
@RestController
@RequestMapping("/goods-seckill")
public class GoodsSeckillController implements InitializingBean {

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    GoodsSeckillServiceImpl goodsSeckillService;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    DefaultRedisScript<Long> script;
    @Autowired
    RedisTemplate redisTemplate;
    //内存标记,减少redis访问量
    private HashMap<Long,Boolean> emptyStockMap=new HashMap();
    //限流
    private RateLimiter rateLimiter=RateLimiter.create(10);

    @RequestMapping("/doSeckill")
    public Result doSeckill(@Valid SysUserVo userVo,long goodsId,HttpServletRequest request){
        //0. 限流
        boolean access = rateLimiter.tryAcquire(1, TimeUnit.SECONDS);
        if (!access){
            return Result.fail(Const.ACCESS_LIMIT_REACHED);
        }
        //1.内存标记,判断库存是否为空
        if (emptyStockMap.get(goodsId)){
            return Result.fail(Const.GOODS_NULL);
        }
        //2.判断redis的库存是否为空
        Integer goodsStock = (Integer) redisUtils.get("seckillGoods" + goodsId);
        if (goodsStock.intValue()<=0){
            return Result.fail(Const.GOODS_NULL);
        }
       Boolean hasKey= redisUtils.hasKey("goods:"+goodsId,userVo.getId());
       //3.通过redis判断是否重复购买
       if(hasKey){
           return  Result.fail(Const.SECKILL_REPEAT);
       }
        String token = request.getHeader("token");
        //4.判断token合法与否
        if (StringUtils.isBlank(token)){
            throw new GlobalException(Const.TOKEN_ERROR);
        }
        //5.通过token和userID从redis里取用户信息
        SysUser user =(SysUser) redisUtils.hget("User" + userVo.getId(), token);
        if (user==null){
            throw new GlobalException(Const.TOKEN_ERROR);
        }
        //6.预减库存
        //Long stock = redisUtils.decrement("seckillGoods"+goodsId);
        //6.预减库存,使用lua脚本
        Long stock = (Long) redisTemplate.execute(script, Collections.singletonList("seckillGoods"+goodsId), Collections.EMPTY_LIST);
        if (stock==0){
        //    redisUtils.increment("goodsStock",""+goodsId);
            emptyStockMap.replace(goodsId,true);
            return Result.fail(Const.GOODS_NULL);
        }
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setGoodsId(goodsId);
        seckillMessage.setUser(user);
//        //7.将秒杀信息发送至mq, mq消费者调用service,执行业务
        rabbitTemplate.convertAndSend("order_Exchange","order", JSONUtil.toJsonStr(seckillMessage));

        return Result.succ(Const.WAIT_ORDER);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsSeckill> list = goodsSeckillService.list();
        if (CollectionUtil.isEmpty(list)){
           return;
        }
        list.forEach(gs->{
//            redisUtils.hset("goodsStock",""+gs.getGoodsId(),gs.getStockCount(),1, TimeUnit.DAYS);
            redisUtils.set("seckillGoods"+gs.getGoodsId(),gs.getStockCount(),1,TimeUnit.DAYS);
            emptyStockMap.put(gs.getGoodsId(),false);
        });
    }
}
