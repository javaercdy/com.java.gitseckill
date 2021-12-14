package com.java.gitseckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.gitseckill.entity.Goods;
import com.java.gitseckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cdy
 * @since 2021-11-30
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    @Select("SELECT goods_name,goods_img,goods_price,seckill_price,stock_count,start_date,end_date FROM sk_goods INNER JOIN sk_goods_seckill ON sk_goods.id=sk_goods_seckill.goods_id")
    List<GoodsVo> selectGoodVo();
}
