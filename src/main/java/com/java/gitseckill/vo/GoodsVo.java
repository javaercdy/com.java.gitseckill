package com.java.gitseckill.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author javaercdy
 * @create 2021-12-01$-{TIME}
 */
@Data
public class GoodsVo {

    private String goodsName;
    private String goodsImg;
    private BigDecimal goodsPrice;
    private BigDecimal seckillPrice;
    private Integer stockCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
