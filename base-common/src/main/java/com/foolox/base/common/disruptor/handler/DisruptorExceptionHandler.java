package com.foolox.base.common.disruptor.handler;

import com.lmax.disruptor.ExceptionHandler;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 20/05/2019
 */
public class DisruptorExceptionHandler implements ExceptionHandler<Object> {

    @Override
    public void handleEventException(Throwable ex, long arg1, Object arg2) {
        ex.printStackTrace();
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
    }

    @Override
    public void handleOnStartException(Throwable ex) {
    }

}