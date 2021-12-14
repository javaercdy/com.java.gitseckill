package com.java.gitseckill.vo;

import com.java.gitseckill.conf.annotation.IsMobile;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

/**
 * @author javaercdy
 * @create 2021-12-01$-{TIME}
 */
@Data
public class SysUserVo {
    @IsMobile(message = "ID格式错误,请重新登陆")
    private String id;
    @NotBlank(message = "用户名不存在,请重新登陆")
    private String nickName;

}
