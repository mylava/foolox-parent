package com.foolox.base.common.util.redis;

import com.foolox.base.common.util.RedisUtil;
import com.foolox.base.constant.game.PlayerStatus;

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
    private static final String HEAD_IMG = "headImg";
    private static final String NICKNAME = "nickname";

    /**
     * 保存用户token
     *
     * @param userId
     * @param token
     */
    public static void saveToken(Long userId, String token) {
        RedisUtil.set(USERID_TOKEN, userId.toString(), token);
    }

    /**
     * 获取用户token
     *
     * @param userId
     */
    public static String getToken(Long userId) {
        return RedisUtil.get(USERID_TOKEN, userId.toString());
    }

    /**
     * 更新token过期时间
     * token小于1小时才更新
     *
     * @param userId
     */
    public static void expireToken(Long userId) {
        if (RedisUtil.exists(USERID_TOKEN, userId.toString())
                && (RedisUtil.getExpireTime(USERID_TOKEN, userId.toString()) < 3600)) {
            RedisUtil.expire(USERID_TOKEN, userId.toString(), USERID_TOKEN.getExpire());
        }
    }

    /**
     * 获取用户状态
     * @param userId
     * @return
     */
    public static PlayerStatus getPlayerStatus(Long userId) {
        String value = RedisUtil.hget(USERID_FIELD, userId.toString(), STATUS, String.class);
        return PlayerStatus.nameOf(value);
    }

    /**
     * 设置用户状态
     * @param userId
     * @return
     */
    public static void setPlayerStatus(Long userId, String status) {
        RedisUtil.hset(USERID_FIELD, userId.toString(), STATUS, status);
    }

    /**
     * 获取用户头像
     * @param userId
     * @return
     */
    public static String getHeadImg(Long userId) {
        return RedisUtil.hget(USERID_FIELD, userId.toString(), HEAD_IMG, String.class);
    }

    /**
     * 设置用户头像
     * @param userId
     * @return
     */
    public static void setHeadImg(Long userId, String headImg) {
        RedisUtil.hset(USERID_FIELD, userId.toString(), HEAD_IMG, headImg);
    }

    /**
     * 获取用户头像
     * @param userId
     * @return
     */
    public static String getNickname(Long userId) {
        return RedisUtil.hget(USERID_FIELD, userId.toString(), NICKNAME, String.class);
    }

    /**
     * 设置用户头像
     * @param userId
     * @return
     */
    public static void setNickname(Long userId, String nickname) {
        RedisUtil.hset(USERID_FIELD, userId.toString(), NICKNAME, nickname);
    }
}
