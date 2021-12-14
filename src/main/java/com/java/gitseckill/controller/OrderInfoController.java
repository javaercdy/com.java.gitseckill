package com.java.gitseckill.controller;


import com.java.gitseckill.entity.OrderInfo;
import com.java.gitseckill.service.impl.OrderInfoServiceImpl;
import com.java.gitseckill.utils.Const;
import com.java.gitseckill.vo.Result;
import com.java.gitseckill.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/order-info")
public class OrderInfoController {
    @Autowired
    OrderInfoServiceImpl orderInfoService;
    @RequestMapping("/orderById")
    public Result orderInfoById(@Valid SysUserVo userVo,Long orderInfoId){
        OrderInfo info = orderInfoService.getById(orderInfoId);
        if (info!=null){
            return Result.succ(info);
        }else{
            return Result.fail(Const.EMPTY_ORDER);
        }
    }

}
