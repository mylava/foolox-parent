package com.foolox.base.constant.rediskey;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 01/08/2019
 */
public class LockPrefix extends BasePrefix {

    private LockPrefix(String prefix) {
        super(ONE_DAY, prefix);
    }

    /**
     * 用户创建房间的锁
     */
    public static final LockPrefix LOCK_CREATEROOM = new LockPrefix("lock:createRoom");
}
