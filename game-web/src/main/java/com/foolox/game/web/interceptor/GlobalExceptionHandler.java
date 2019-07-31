package com.foolox.game.web.interceptor;

import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.constant.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * comment: 异常处理器
 *
 * @author: lipengfei
 * @date: 23/08/2018
 * @company: (C) Copyright 58BTC 2018
 * @since: JDK 1.8
 * @description:
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHanlder(HttpServletRequest request, Exception e) {
        if (e instanceof BindException) {
            BindException be = (BindException) e;
            List<ObjectError> errors = be.getAllErrors();
            String[] args = new String[errors.size()];
            for (int i=0;i<errors.size();i++) {
                args[i] = errors.get(i).getDefaultMessage();
            }
            return Result.fail(CodeMessage.VALIDATE_ERROR.fillArgs((Object[]) args));
        } else if(e instanceof HttpMessageNotReadableException) {
            return Result.fail(CodeMessage.VALIDATE_ERROR.fillArgs(e.getMessage()));
        } else {
            log.error(e.toString(),e);
            //STAY 不同异常类型处理
            return Result.fail(CodeMessage.SERVER_ERROR);
        }

    }
}
