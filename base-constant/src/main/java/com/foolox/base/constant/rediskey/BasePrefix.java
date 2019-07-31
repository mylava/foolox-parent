package com.foolox.base.constant.rediskey;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 22/08/2018
 * @company: (C) Copyright 58BTC 2018
 * @since: JDK 1.8
 * @description:
 */
public abstract class BasePrefix implements KeyPrefix {

    private static final int DEFAULT_EXPIRE = 3600 * 24 * 2;
    private int expireSeconds;

    private String prefix;

    //abstract构造永远都不能直接new，子类可以通过super调
    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    //希望userkey永不过期
    public BasePrefix(String prefix) {
        this(DEFAULT_EXPIRE, prefix);
    }

    @Override
    public int getExpire() {
        return this.expireSeconds;
    }

    /**
     * @return
     */
    @Override
    public String getPrefix() {
//        String simpleName = getClass().getSimpleName();
        return this.prefix + ":";
    }
}
