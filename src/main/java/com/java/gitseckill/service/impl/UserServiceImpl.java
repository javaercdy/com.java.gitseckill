package com.java.gitseckill.service.impl;

import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.gitseckill.entity.SysUser;
import com.java.gitseckill.execption.GlobalException;
import com.java.gitseckill.mapper.UserMapper;
import com.java.gitseckill.service.IUserService;
import com.java.gitseckill.utils.Const;
import com.java.gitseckill.utils.MD5Utils;
import com.java.gitseckill.utils.RedisUtils;
import com.java.gitseckill.vo.LoginVo;
import com.java.gitseckill.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cdy
 * @since 2021-11-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisUtils redisUtils;

    @Override
    public Result  login(@Valid LoginVo loginVo, HttpServletResponse response) {
        SysUser sysUser = userMapper.selectById(loginVo.getMobile());

        if (sysUser==null){
            return Result.fail(Const.User_NULL);
        }
        String dbPass= sysUser.getPassword();

        String slat = sysUser.getSalt();

        String formPass = loginVo.getPassword();

        String password = MD5Utils.formPassToDbPass(formPass, slat);

        if (!dbPass.equals(password)){
            return Result.fail(Const.User_NULL);
        }
        //生成token
        String token = UUID.randomUUID().toString().replace("_"," ");

        //查询时,任然需要UserID,放在redis里是为了不去数据库
        redisUtils.hset("User"+loginVo.getMobile(),token,sysUser,1, TimeUnit.DAYS);

        return Result.succ("请求成功",token);
    }
}
