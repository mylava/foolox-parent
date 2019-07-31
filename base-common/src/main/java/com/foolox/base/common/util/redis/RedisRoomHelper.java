package com.foolox.base.common.util.redis;

import com.foolox.base.common.util.RedisUtil;
import com.foolox.base.constant.rediskey.RoomPrefix;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 29/07/2019
 */
public class RedisRoomHelper {

    /**
     * 通过 userId 获取 RoomId
     *
     * @param userId
     * @return
     */
    public static String getRoomIdByUserId(String userId) {
        return RedisUtil.get(RoomPrefix.USERID_GAMEROOMID, userId);
    }
}
