package com.foolox.base.io.dispatch;

import java.lang.reflect.Method;

/**
 * comment:消息执行单元封装
 *
 * @author: lipengfei
 * @date: 16/07/2019
 */
public class CmdExecutor {
    /** logic handler method */
    private Method method;
    /** arguments passed to method */
    private Class<?>[] params;
    /** logic facade */
    private Object handler;

    public static CmdExecutor valueOf(Method method, Class<?>[] params, Object handler) {
        CmdExecutor executor = new CmdExecutor();
        executor.method = method;
        executor.params = params;
        executor.handler = handler;

        return executor;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?>[] getParams() {
        return params;
    }

    public Object getHandler() {
        return handler;
    }
}
