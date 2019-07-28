package com.foolox.base.constant.annotation;

import java.lang.annotation.*;

/**
 * 消息元信息
 * 此注解用于指定消息的 module 和 cmd
 *
 * @author lipengfei
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageMeta {

	short module() default 0;

	short cmd() default 0;

}
