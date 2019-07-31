package com.foolox.base.constant.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Facade {
    MessageEvent value();
}
