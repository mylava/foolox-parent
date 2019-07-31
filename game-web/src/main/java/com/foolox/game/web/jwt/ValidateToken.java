package com.foolox.game.web.jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * comment: 需要验证Token才能进行后续操作的注解
 *
 * @author: lipengfei
 * @date: 12/05/2019
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateToken {
    boolean required() default true;
}
