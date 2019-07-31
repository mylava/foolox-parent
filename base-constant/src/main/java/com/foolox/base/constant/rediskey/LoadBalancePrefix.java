package com.foolox.base.constant.rediskey;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
public class LoadBalancePrefix extends BasePrefix {
    private static final int ONE_DAY = 3600*24;
    private LoadBalancePrefix(String prefix) {
        super(ONE_DAY,prefix);
    }

    public static final LoadBalancePrefix MACHINE = new LoadBalancePrefix("loadbalance");
}
