package com.java.gitseckill.vo;

import com.java.gitseckill.utils.Const;
import lombok.Data;

/**
 * @author javaercdy
 * @create 2021-11-29$-{TIME}
 */
@Data
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result succ(String message,Object data){
        return new Result(200,message,data);
    }
    public static Result succ(Object data){
       return succ("请求成功",data);
    }

    public static Result succ(String message){
        return succ(message,null);
    }
    public static Result fail(Integer code,String message,Object object){
        return new Result(code,message,object);
    }
    public static Result fail(Integer code,String message){
        return Result.fail(code,message,null);
    }
    public static Result fail(Integer code){
        return Result.fail(code,"请求错误",null);
    }

    public static Result fail(Const error){
      return fail(error.getCode(),error.getMessage());
    }
    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
