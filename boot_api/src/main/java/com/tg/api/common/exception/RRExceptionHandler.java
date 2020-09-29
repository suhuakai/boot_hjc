package com.tg.api.common.exception;

import com.tg.api.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestControllerAdvice
public class RRExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 处理自定义异常
     */

    @ExceptionHandler(RRException.class)
    public R handleRRException(RRException e) {
        return R.error(e.getCode()+"",e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public R handleRidst(Exception e) {
        e.printStackTrace();
        return R.error("未知异常,请联系管理员");
    }
}
