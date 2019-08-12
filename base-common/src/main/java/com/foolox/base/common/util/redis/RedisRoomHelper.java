package com.foolox.base.common.util.redis;

import com.foolox.base.common.util.RedisUtil;
import com.foolox.base.constant.rediskey.RoomPrefix;

import java.util.List;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 29/07/2019
 */
public class RedisRoomHelper {

    /**
     * 通过 playerId 获取 所在房间的 RoomNo
     *
     * @param playerId
     * @return
     */
    public static String getRoomNoByPlayerId(Long playerId) {
        return RedisUtil.get(RoomPrefix.PLAYERID_GAMEROOMNO, playerId.toString());
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

    /**
     * 获取所有在房间内的玩家ID
     * @param roomNo
     * @return
     */
    public static List<Long> getRoomPlayerIdList(String roomNo) {
        return RedisUtil.lrange(RoomPrefix.ROOMNO_PLAYERID_LIST, roomNo, 0, -1, Long.class);
    }

    /**
     * 保存 playerId 到 roomId 与 List<playerId> 映射关系到缓存
     * @param roomId
     * @param playerId
     */
    public static void addRoomPlayerId(String roomId, Long playerId) {
        RedisUtil.lpush(RoomPrefix.ROOMNO_PLAYERID_LIST, roomId, playerId);
    }

    /**
     * 保存 playerId 到 roomNo 与 List<playerId> 映射关系到缓存
     * @param roomNo
     * @param playerId
     */
    public static <T> void addRoomPlayer(String roomNo, Long playerId, T value) {
        RedisUtil.hset(RoomPrefix.ROOMNO_PLAYER_MAP, roomNo, playerId.toString(), value);
    }
}
