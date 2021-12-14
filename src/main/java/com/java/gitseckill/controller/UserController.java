package com.java.gitseckill.controller;


import com.java.gitseckill.entity.SysUser;
import com.java.gitseckill.utils.Const;
import com.java.gitseckill.utils.RedisUtils;
import com.java.gitseckill.vo.Result;
import com.java.gitseckill.vo.SysUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cdy
 * @since 2021-11-19
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    RedisUtils redisUtils;

    @RequestMapping("/userInfo")
    public Result userInfo(@Valid SysUserVo userVo, HttpServletRequest request){
        String token = request.getHeader("token");
        if (!StringUtils.isBlank(token)){
            return Result.fail(Const.TOKEN_ERROR);
        }
        SysUser sysUser = (SysUser) redisUtils.hget("User" + userVo.getId(), token);
        if (sysUser==null){
            return Result.fail(Const.TOKEN_ERROR);
        }
        return  Result.succ(sysUser);
    }

}
