package com.foolox.base.common.util.redis;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 22/08/2018
 * @company: (C) Copyright 58BTC 2018
 * @since: JDK 1.8
 * @description:
 */
public interface KeyPrefix {
    int getExpire();
    String getPrefix();
}
