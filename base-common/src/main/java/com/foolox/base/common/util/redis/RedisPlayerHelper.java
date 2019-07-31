package com.foolox.base.common.util.redis;

import com.foolox.base.common.util.RedisUtil;
import com.foolox.base.constant.poker.PlayerStatus;

import static com.foolox.base.constant.rediskey.PlayerPrefix.USERID_FIELD;
import static com.foolox.base.constant.rediskey.PlayerPrefix.USERID_TOKEN;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 29/07/2019
 */
public class RedisPlayerHelper {

    private static final String STATUS = "status";

    /**
     * 保存用户token
     *
     * @param userId
     * @param token
     */
    public static void saveToken(String userId, String token) {
        RedisUtil.set(USERID_TOKEN, userId, token);
    }

    /**
     * 获取用户token
     *
     * @param userId
     */
    public static String getToken(String userId) {
        return RedisUtil.get(USERID_TOKEN, userId);
    }

    /**
     * 更新token过期时间
     * token小于1小时才更新
     *
     * @param userId
     */
    public static void expireToken(String userId) {
        if (RedisUtil.exists(USERID_TOKEN, userId)
                && (RedisUtil.getExpireTime(USERID_TOKEN, userId) < 3600)) {
            RedisUtil.expire(USERID_TOKEN, userId, USERID_TOKEN.getExpire());
        }
    }

    /**
     * 获取用户状态
     * @param userId
     * @return
     */
    public static PlayerStatus getPlayerStatus(String userId) {
        String value = RedisUtil.hget(USERID_FIELD, userId, STATUS, String.class);
        return PlayerStatus.nameOf(value);
    }


}
