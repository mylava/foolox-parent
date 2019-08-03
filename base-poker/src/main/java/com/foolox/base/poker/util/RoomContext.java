package com.foolox.base.poker.util;

import com.foolox.base.poker.domain.GameRoom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 03/08/2019
 */
public class RoomContext {
    private static final Map<String, GameRoom> rooms = new ConcurrentHashMap<>();

    public static GameRoom getRoom(String roomNo) {
        return rooms.get(roomNo);
    }

    public static GameRoom saveRoom(String roomNo, GameRoom gameRoom) {
        return rooms.putIfAbsent(roomNo, gameRoom);
    }

    public static boolean existRoomNo(String roomNo) {
        return rooms.containsKey(roomNo);
    }
}
