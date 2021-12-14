package com.java.gitseckill.service.impl;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.gitseckill.entity.*;
import com.java.gitseckill.execption.GlobalException;
import com.java.gitseckill.mapper.OrderInfoMapper;
import com.java.gitseckill.mapper.OrderMapper;
import com.java.gitseckill.service.IGoodsSeckillService;
import com.java.gitseckill.service.IOrderService;
import com.java.gitseckill.utils.Const;
import com.java.gitseckill.utils.RedisUtils;
import com.java.gitseckill.vo.Result;
import com.sun.corba.se.spi.ior.IdentifiableFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cdy
 * @since 2021-11-30
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements IOrderService{
    @Autowired
    GoodsSeckillServiceImpl goodsSeckillService;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    GoodsServiceImpl goodsService;
    @Autowired
    OrderInfoServiceImpl orderInfoService;
    @Autowired
    OrderInfoMapper orderInfoMapper;

    /**
     * 保证秒杀的事务性
     * @param goodsId
     * @param userId
     * @return
     */

//    @Transactional
//    @Override
//    public Result secKill(long goodsId, long userId) {
//
//        GoodsSeckill goodsSeckill = goodsSeckillService.getOne(new QueryWrapper<GoodsSeckill>().eq("goods_id", goodsId));
//        //1.更新库存
//        boolean stock_count = goodsSeckillService.update(new UpdateWrapper<GoodsSeckill>()
//                .setSql("stock_count=stock_count-1")
//                .eq("stock_count",goodsSeckill.getStockCount())
//                .eq("goods_id",goodsId).gt("stock_count",0));
//        //判断是否成功
//        if (!stock_count){
//           return Result.fail(Const.SECKILL_ERROR);
//        }
//        //2.生成秒杀订单
//        Order order = new Order();
//        order.setUserId(userId);
//        order.setGoodsId(goodsId);
//        orderService.save(order);
//        //4.秒杀信息放入redis里,放入redis里,下次直接判断用户是否重复抢购,不用查询数据库
//        redisUtils.Sadd("goods:"+goodsId,userId);
//        //5.秒杀信息放入rabbitMq中
//
//        return Result.succ(order);
//    }

    /**
     * 优化后
     * @param goodsId
     * @param user
     * @return
     */

    @Transactional
    @Override
    public Result secKill(SysUser user, long goodsId) {

        //获取商品信息
        Goods goods = goodsService.getById(goodsId);
        //生成订单详情
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGoodId(goodsId);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getGoodsPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderInfo.setCreateDate(LocalDateTime.now());
        //这样可以得到插入的orderInfo的id
        orderInfoMapper.insert(orderInfo);

        //生成秒杀订单
        Order order = new Order();
        order.setGoodsId(goodsId);
        order.setUserId(user.getId());
        order.setOrderId(orderInfo.getId());
        try {
            orderService.save(order);
        }catch (Exception e) {
            redisUtils.increment("seckillGoods"+goodsId);
            throw new GlobalException(Const.SECKILL_REPEAT);
        }
        //减少库存
        GoodsSeckill goodsSeckill = goodsSeckillService.findByGoodsId(goodsId);
        try {
            goodsSeckillService.update(new UpdateWrapper<GoodsSeckill>()
                    .setSql("stock_count=stock_count-1")
                    .eq("goods_id", goodsId)
                    .eq("stock_count", goodsSeckill.getStockCount())
                    .gt("stock_count", 0));
        }catch (Exception e){
            redisUtils.increment("seckillGoods"+goodsId);
            throw new GlobalException(Const.GOODS_NULL);
        }
        //秒杀信息放入redis里,放入redis里,下次直接判断用户是否重复抢购,不用查询数据库
        //返回之是0,代表重复插入,以此来判断,是否重复抢购
        Long sadd = redisUtils.Sadd("goods:" + goodsId, user.getId());
        if (sadd==0) {
            redisUtils.increment("seckillGoods"+goodsId);
            throw new GlobalException(Const.SECKILL_REPEAT);
        }
        System.out.println("订单的信息: "+JSONUtil.toJsonStr(orderInfo));
        return Result.succ(orderInfo);
    }

}
