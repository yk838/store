package com.cy.store.service.ex;

/**
 * 用户插入异常（服务器、数据库宕机）
 * @author yk
 * @date 2022/3/13  11:09
 */
public class InsertException extends ServiceException {
    public InsertException() {
    }

    public InsertException(String message) {
        super(message);
    }

    public InsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertException(Throwable cause) {
        super(cause);
    }

    public InsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}