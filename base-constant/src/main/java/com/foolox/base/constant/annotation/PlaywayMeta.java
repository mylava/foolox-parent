package com.foolox.base.constant.annotation;

import com.foolox.base.constant.game.GameType;

import java.lang.annotation.*;

/**
 * comment: 玩法相关元信息
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlaywayMeta {
    GameType value();
}
