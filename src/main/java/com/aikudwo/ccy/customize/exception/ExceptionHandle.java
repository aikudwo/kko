package com.aikudwo.ccy.customize.exception;

import com.aikudwo.ccy.customize.advice.result.WebResult;
import com.aikudwo.ccy.customize.exception.customizeException.BusinessException;
import com.aikudwo.ccy.customize.exception.customizeException.DingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wls
 * @date 2019-05-15 10:20
 * 异常处理
 */
@RestControllerAdvice
public class ExceptionHandle {
    private final static Logger log = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(BusinessException.class)
    public WebResult BusinesExceptionHandler(BusinessException businessException) {
        return new WebResult(200, businessException.getMessage(), "", false);
    }

    @ExceptionHandler(DingException.class)
    public WebResult DingExceptionnHandler(DingException dingException) {
        return new WebResult(200, dingException.getMessage(), "", false);
    }
}
