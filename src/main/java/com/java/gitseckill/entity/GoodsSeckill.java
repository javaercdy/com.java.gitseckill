package com.java.gitseckill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author cdy
 * @since 2021-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sk_goods_seckill")
public class GoodsSeckill implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private Long goodsId;

    private BigDecimal seckillPrice;

    private Integer stockCount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer version;


}
