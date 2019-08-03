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
     * 通过 userId 获取 所在房间的 RoomNo
     *
     * @param userId
     * @return
     */
    public static String getRoomNoByUserId(Long userId) {
        return RedisUtil.get(RoomPrefix.USERID_GAMEROOMNO, userId.toString());
    }

    /**
     * 保存 room 信息到redis，key为roomNo
     * @param roomNo
     * @return
     */
    public static <T> void saveRoom(String roomNo, T room) {
        RedisUtil.set(RoomPrefix.ROOMNO_GAMEROOM, roomNo, room);
    }

    /**
     * 通过 roomNo 获取 room 信息
     * @param roomNo
     * @return
     */
    public static <T> T getRoomByNo(String roomNo, Class<T> clazz) {
        return RedisUtil.get(RoomPrefix.ROOMNO_GAMEROOM, roomNo, clazz);
    }

    /**
     *
     * @param roomNo
     * @return
     */
    public static Boolean existRoomNo(String roomNo) {
        return RedisUtil.exists(RoomPrefix.ROOMNO_GAMEROOM, roomNo);
    }
}
