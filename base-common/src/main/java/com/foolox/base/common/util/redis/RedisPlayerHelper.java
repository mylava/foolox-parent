package com.foolox.base.common.util.redis;

import com.foolox.base.common.util.RedisUtil;
import com.foolox.base.constant.game.PlayerStatus;
import com.foolox.base.constant.rediskey.LockPrefix;

import static com.foolox.base.constant.rediskey.PlayerPrefix.PLAYERID_FIELD;
import static com.foolox.base.constant.rediskey.PlayerPrefix.PLAYERID_TOKEN;

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
    private static final String ROOMNO = "roomno";
//    private static final String NICKNAME = "nickname";

    /**
     * 保存用户token
     *
     * @param playerId
     * @param token
     */
    public static void saveToken(Long playerId, String token) {
        RedisUtil.set(PLAYERID_TOKEN, playerId.toString(), token);
    }

    /**
     * 获取用户token
     *
     * @param playerId
     */
    public static String getToken(Long playerId) {
        return RedisUtil.get(PLAYERID_TOKEN, playerId.toString());
    }

    /**
     * 更新token过期时间
     * token小于1小时才更新
     *
     * @param playerId
     */
    public static void expireToken(Long playerId) {
        if (RedisUtil.exists(PLAYERID_TOKEN, playerId.toString())
                && (RedisUtil.getExpireTime(PLAYERID_TOKEN, playerId.toString()) < 3600)) {
            RedisUtil.expire(PLAYERID_TOKEN, playerId.toString(), PLAYERID_TOKEN.getExpire());
        }
    }

    /**
     * 获取用户状态
     * @param playerId
     * @return
     */
    public static PlayerStatus getPlayerStatus(Long playerId) {
        String value = RedisUtil.hget(PLAYERID_FIELD, playerId.toString(), STATUS, String.class);
        return PlayerStatus.nameOf(value);
    }

    /**
     * 设置用户状态
     * @param playerId
     * @return
     */
    public static void setPlayerStatus(Long playerId, String status) {
        RedisUtil.hset(PLAYERID_FIELD, playerId.toString(), STATUS, status);
    }

    /**
     * 获取用户头像
     * @param playerId
     * @return
     */
    public static String getHeadImg(Long playerId) {
        return RedisUtil.hget(PLAYERID_FIELD, playerId.toString(), HEAD_IMG, String.class);
    }

    /**
     * 设置用户头像
     * @param playerId
     * @return
     */
    public static void setHeadImg(Long playerId, String headImg) {
        RedisUtil.hset(PLAYERID_FIELD, playerId.toString(), HEAD_IMG, headImg);
    }

    /**
     * 获取用户头像
     * @param playerId
     * @return
     */
    public static String getNickname(Long playerId) {
        return RedisUtil.hget(PLAYERID_FIELD, playerId.toString(), NICKNAME, String.class);
    }

    /**
     * 设置用户头像
     * @param playerId
     * @return
     */
    public static void setNickname(Long playerId, String nickname) {
        RedisUtil.hset(PLAYERID_FIELD, playerId.toString(), NICKNAME, nickname);
    }

    /**
     *
     * @param playerId
     * @param seconds
     * @return
     */
    public static boolean lockKey(Long playerId, int seconds) {
        return RedisUtil.lockKey(LockPrefix.LOCK_CREATEROOM, playerId.toString(), seconds);
    }

    /**
     * 保存 playerId 与 gameRoomNo 映射关系到缓存
     *
     * @param playerId
     * @param roomNo
     * @return
     */
    public static void setRoomNo(Long playerId, String roomNo) {
        RedisUtil.hset(PLAYERID_FIELD, playerId.toString(), ROOMNO, roomNo);
    }
}
