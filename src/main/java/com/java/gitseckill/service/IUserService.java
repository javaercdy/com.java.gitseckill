package com.java.gitseckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gitseckill.entity.SysUser;
import com.java.gitseckill.vo.LoginVo;
import com.java.gitseckill.vo.Result;

import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cdy
 * @since 2021-11-19
 */
public interface IUserService extends IService<SysUser> {

    Result login(LoginVo loginVo, HttpServletResponse response);
}
