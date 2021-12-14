package com.java.gitseckill.vo;

import com.java.gitseckill.conf.annotation.IsMobile;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author javaercdy
 * @create 2021-11-30$-{TIME}
 */
@Data
public class LoginVo {

    @NotBlank(message = "手机号码不能为空")
    @IsMobile(message = "手机号格式错误")
    private String mobile;
    @NotBlank(message = "密码不能为空")
    private String password;
//    @NotBlank(message = "验证码不能为空")
    private String code;
}
