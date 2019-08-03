package com.foolox.base.common.util;

import org.springframework.context.ApplicationContext;

/**
 * comment: spring 容器工具
 *
 * @author: lipengfei
 * @date: 26/07/2019
 */
public class SpringContextUtil {
    //系统启动时，将spring容器注入进来，getBean时使用
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }

    public static <T> T getBean(String beanName, Class<T> clz) {
        return applicationContext.getBean(beanName, clz);
    }

}
