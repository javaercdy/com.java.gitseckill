package com.java.gitseckill.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.java.gitseckill.execption.GlobalException;
import com.java.gitseckill.service.impl.UserServiceImpl;
import com.java.gitseckill.utils.Const;
import com.java.gitseckill.utils.RedisUtils;
import com.java.gitseckill.vo.LoginVo;
import com.java.gitseckill.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author javaercdy
 * @create 2021-11-30$-{TIME}
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    DefaultKaptcha defaultKaptcha;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/captcha")
    public Result createCode() throws IOException {

        //验证码的key,
        String key=UUID.randomUUID().toString().replace("_"," ");
        //验证码
        String code = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpeg",outputStream);

        BASE64Encoder base64Encoder = new BASE64Encoder();
        String str="data:image/jpeg;base64,";
        String img= str+base64Encoder.encode(outputStream.toByteArray());

        redisUtils.hset("CAPTCHA",key,code,60,TimeUnit.SECONDS);

        return Result.succ("请求成功", MapUtil.builder()
                .put("captchaKey",key)
                .put("img",img)
                .build());
    }

    @PostMapping(value = "/login")
    public Result login(@Valid LoginVo loginVo, HttpServletResponse httpResponse){
        if (loginVo.getMobile()==null){
            throw new GlobalException(Const.ARGUMENT_ERROR);
        }
        return userService.login(loginVo,httpResponse);
    }
}
