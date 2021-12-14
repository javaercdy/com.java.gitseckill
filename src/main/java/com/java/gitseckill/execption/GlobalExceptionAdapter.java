package com.java.gitseckill.execption;

import com.java.gitseckill.utils.Const;
import com.java.gitseckill.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author javaercdy
 * @create 2021-11-30$-{TIME}
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionAdapter {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result hanlder(MethodArgumentNotValidException e){
        log.error("-----实体类校验异常-----{}",e.getMessage());
        return Result.fail(Const.ARGUMENT_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value={RuntimeException.class})
    public Result handler(RuntimeException e){
        if (e instanceof GlobalException){
            GlobalException ex = (GlobalException)e;
            log.error("-----运行时全局异常-----{}",ex.getErrorConst().getMessage());
            return Result.fail(ex.getErrorConst());
        }else{
            log.error("-----运行时异常-----{}",e.getMessage());
            return Result.fail(Const.SERVER_ERROR);
        }
    }
}
