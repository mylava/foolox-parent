package com.foolox.base.common.util;

import org.springframework.context.ApplicationContext;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 26/07/2019
 */
public class ContextUtil {
    //系统启动时，将spring容器注入进来，getBean时使用
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ContextUtil.applicationContext = applicationContext;
    }
}
