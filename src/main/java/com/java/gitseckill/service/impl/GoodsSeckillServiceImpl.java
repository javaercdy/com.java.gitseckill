package com.java.gitseckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.gitseckill.entity.GoodsSeckill;
import com.java.gitseckill.mapper.GoodsSeckillMapper;
import com.java.gitseckill.service.IGoodsSeckillService;
import com.java.gitseckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cdy
 * @since 2021-11-30
 */
@Service
public class GoodsSeckillServiceImpl extends ServiceImpl<GoodsSeckillMapper, GoodsSeckill> implements IGoodsSeckillService {
    @Autowired
    GoodsSeckillMapper goodsSeckillMapper;
    @Override
    public GoodsSeckill  findByGoodsId(long goodsId) {
        GoodsSeckill goodsSeckill = goodsSeckillMapper.selectByGoodsId(goodsId);
        return goodsSeckill;
    }
}
