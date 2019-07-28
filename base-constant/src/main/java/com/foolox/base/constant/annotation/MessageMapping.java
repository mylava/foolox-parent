package com.foolox.base.constant.annotation;

import java.lang.annotation.*;

/**
 * comment: 分发器根据这个注解进行消息分发
 *
 * @author: lipengfei
 * @date: 17/07/2019
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageMapping {
}
