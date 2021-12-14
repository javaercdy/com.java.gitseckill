package com.java.gitseckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gitseckill.entity.Order;
import com.java.gitseckill.entity.SysUser;
import com.java.gitseckill.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cdy
 * @since 2021-11-30
 */
public interface IOrderService extends IService<Order> {

    public Result secKill(SysUser user, long goodsId);
}
