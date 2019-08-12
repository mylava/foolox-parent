package com.foolox.base.constant.annotation;

import com.foolox.base.constant.game.GameType;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * comment: 策略注解
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Strategy {
    GameType type();
}
