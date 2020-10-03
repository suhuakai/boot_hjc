package com.tg.admin.common.exception;

import com.tg.admin.common.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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
	public R handleRRException(RRException e){
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());
		return r;
	}


	@ExceptionHandler(BindException.class)
	public R handleBindException(BindException e){
		BindingResult bindingResult = e.getBindingResult();
		if (bindingResult.hasErrors()){
			for (ObjectError oe : bindingResult.getAllErrors()){
				String defaultMessage = oe.getDefaultMessage();
				if (StringUtils.isNotBlank(defaultMessage)){
					return R.error(defaultMessage);
				}
			}
		}
		return R.error("参数校验失败");
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public R handleConstraintViolationException(ConstraintViolationException e){
		for (ConstraintViolation c : e.getConstraintViolations()){
			String message = c.getMessage();
			if (StringUtils.isNotEmpty(message)){
				return R.error(message);
			}
		}
		return R.error("参数校验失败");
	}


}
