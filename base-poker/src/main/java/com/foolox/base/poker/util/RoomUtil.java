package com.foolox.base.poker.util;

import com.foolox.base.constant.result.CommonMessage;
import com.foolox.base.db.domain.GameRoom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 03/08/2019
 */
public class RoomUtil {
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

    /**
     * 发送消息给房间内所有玩家
     * @param roomNo
     * @param commonMessage
     */
    public static void sendAllMessage(String roomNo, CommonMessage commonMessage) {

    }

    /**
     * 发送消息给房间部分玩家
     * @param roomNo
     * @param commonMessage
     * @param except
     */
    public static void sendPartMessage(String roomNo, CommonMessage commonMessage, String except) {

    }

    /**
     * 给自己和其他玩家发送不同消息
     * @param roomNo
     * @param selfMessage
     * @param othersMessage
     */
    public static void sendDiffMessage(String roomNo, CommonMessage selfMessage, CommonMessage othersMessage) {

    }

}
