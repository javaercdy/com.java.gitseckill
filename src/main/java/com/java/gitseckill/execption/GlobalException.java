package com.java.gitseckill.execption;

import com.java.gitseckill.utils.Const;
import org.springframework.stereotype.Component;

/**
 * @author javaercdy
 * @create 2021-11-30$-{TIME}
 */

public class GlobalException extends RuntimeException {

    private Const errorConst;

    public GlobalException(Const errorConst) {
        super(errorConst.toString());
        this.errorConst=errorConst;
    }

    public Const getErrorConst() {
        return errorConst;
    }

    public void setErrorConst(Const errorConst) {
        this.errorConst = errorConst;
    }

}
