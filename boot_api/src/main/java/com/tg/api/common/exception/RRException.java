package com.tg.api.common.exception;

import lombok.Data;

/**
 * 自定义异常
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class RRException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code = 201;
    private String msg;

    public RRException(String msg) {
        this.msg = msg;
    }

    public RRException(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

}
