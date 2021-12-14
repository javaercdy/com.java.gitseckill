package com.java.gitseckill.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.gitseckill.entity.GoodsSeckill;
import com.java.gitseckill.entity.Order;
import com.java.gitseckill.entity.SysUser;
import com.java.gitseckill.service.impl.GoodsSeckillServiceImpl;
import com.java.gitseckill.service.impl.OrderServiceImpl;
import com.java.gitseckill.utils.Const;
import com.java.gitseckill.vo.Result;
import com.java.gitseckill.vo.SysUserVo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cdy
 * @since 2021-11-30
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    GoodsSeckillServiceImpl goodsSeckillService;
    @RequestMapping("/oderByGoodsId")
    public Result seckillOrder(SysUser user, Long goodsId){
        if (user==null){
            return Result.fail(Const.User_NULL);
        }
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("user_id",user.getId()).eq("goods_id",goodsId));
        if (order==null){
            GoodsSeckill goodsSeckill = goodsSeckillService.findByGoodsId(goodsId);
            if (goodsSeckill.getStockCount()==0){
                return Result.fail(Const.GOODS_NULL);
            }
            return  Result.fail(Const.EMPTY_ORDER);
        }
        return Result.succ(order);
    }

}
