package com.java.gitseckill.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.gitseckill.entity.GoodsSeckill;
import com.java.gitseckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cdy
 * @since 2021-11-30
 */
public interface GoodsSeckillMapper extends BaseMapper<GoodsSeckill> {


    @Select("select stock_count from sk_goods_seckill where goods_id=#{goodsId}")
    GoodsSeckill selectByGoodsId(long goodsId);
}
