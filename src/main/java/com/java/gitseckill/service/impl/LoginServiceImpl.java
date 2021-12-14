package com.java.gitseckill.service.impl;

import com.java.gitseckill.service.LoginService;
import com.java.gitseckill.utils.Const;
import com.java.gitseckill.utils.RedisUtils;
import com.java.gitseckill.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author javaercdy
 * @create 2021-12-01$-{TIME}
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    RedisTemplate redisTemplate;
    public Result doCaptcha(String codeKey,String code){

        Object redisCode = redisUtils.hget("CAPTCHA", codeKey);

        if (!code.equals(redisCode)){
            return  Result.fail(Const.CAPTCHA_ERROR);
        }
        return Result.succ("验证码正确");
    }
}
