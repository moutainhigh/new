package com.huayu.common;

/**
 * Created by DengPeng on 2016/11/21.
 */

public class BusinessException extends RuntimeException{

    public BusinessException() {

    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message,Throwable cause) {
        super(message,cause);
    }

}
