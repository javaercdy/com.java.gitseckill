package com.java.gitseckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.java.gitseckill.entity.OrderInfo;
import com.java.gitseckill.mapper.OrderInfoMapper;
import com.java.gitseckill.service.IOrderInfoService;
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
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

}
