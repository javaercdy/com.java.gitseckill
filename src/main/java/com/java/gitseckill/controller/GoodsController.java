package com.java.gitseckill.controller;


import com.java.gitseckill.service.impl.GoodsServiceImpl;
import com.java.gitseckill.vo.GoodsVo;
import com.java.gitseckill.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cdy
 * @since 2021-11-30
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsServiceImpl goodsService;

    /**
     * 获取秒杀商品列表
     * @return
     */
    @RequestMapping("/list")
    public Result listGoods(){

        List<GoodsVo> list = goodsService.findSecGoodVo();
        return Result.succ(list);
    }
}
