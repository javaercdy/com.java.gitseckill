package com.java.gitseckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gitseckill.entity.GoodsSeckill;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cdy
 * @since 2021-11-30
 */
public interface IGoodsSeckillService extends IService<GoodsSeckill> {

    GoodsSeckill findByGoodsId(long goodsId);

}
