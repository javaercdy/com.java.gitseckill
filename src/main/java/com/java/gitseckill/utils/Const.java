package com.java.gitseckill.utils;

/**
 * @author javaercdy
 * @create 2021-11-30$-{TIME}
 */
public enum Const {

    SERVER_ERROR(5001,"服务器错误"),
    ARGUMENT_ERROR(5002,"参数校验异常"),
    User_NULL(5003,"用户不存在"),
    CAPTCHA_ERROR(5004,"验证码错误"),
    TOKEN_ERROR(5005,"TOKEN错误,请重新登陆"),
    GOODS_NULL(5006,"商品库存为空"),
    //秒杀模块
    SECKILL_REPEAT(5007,"用户重复抢购"),
    SECKILL_ERROR(5009,"抢购失败"),
    RABBITMQ_ERROR(5010,"rabbitmq订单生成失败"),
    EMPTY_ORDER(5008,"订单为空"),
    WAIT_ORDER(50011,"排队中"),
    ACCESS_LIMIT_REACHED(50012,"访问次数过多,请稍等")
    ;


    private int code;
    private String message;

    Const(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
