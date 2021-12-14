package com.java.gitseckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.java.gitseckill.entity.Goods;
import com.java.gitseckill.mapper.GoodsMapper;
import com.java.gitseckill.service.IGoodsService;
import com.java.gitseckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cdy
 * @since 2021-11-30
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    public List<GoodsVo> findSecGoodVo() {
        List<GoodsVo> goodsVos=goodsMapper.selectGoodVo();

        return goodsVos;
    }
}
