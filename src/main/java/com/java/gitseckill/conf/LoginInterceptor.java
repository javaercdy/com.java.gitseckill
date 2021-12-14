package com.java.gitseckill.conf;


import com.java.gitseckill.service.impl.LoginServiceImpl;
import com.java.gitseckill.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author javaercdy
 * @create 2021-12-01$-{TIME}
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    LoginServiceImpl loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if ("/login/login".equals(request.getRequestURI())&&request.getMethod().equals("POST")){
            String codekey = request.getParameter("key");
            String code =request.getParameter("code");
            if (StringUtils.isEmpty(code)||StringUtils.isEmpty(codekey)){
                return false;
            }
            Result result = loginService.doCaptcha(codekey, code);
            if (result.getCode()!=200){
                return false;
            }
        }

        return true;


    }
}
