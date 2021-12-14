package com.java.gitseckill.controller;

import com.java.gitseckill.entity.GoodsSeckill;
import com.java.gitseckill.service.impl.GoodsSeckillServiceImpl;
import com.java.gitseckill.service.impl.OrderServiceImpl;
import com.java.gitseckill.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author javaercdy
 * @create 2021-12-01$-{TIME}
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    GoodsSeckillServiceImpl goodsSeckillService;
    @Transactional
    @RequestMapping
    public Result test(){
        GoodsSeckill byId = goodsSeckillService.getById(1);
        byId.setStockCount(2);
        goodsSeckillService.updateById(byId);
//        int i=1/0;
        return Result.succ(byId);
    }
}
