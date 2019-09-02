package com.foolox.base.common.util.redis;

import com.foolox.base.common.util.RedisUtil;
import com.foolox.base.constant.rediskey.BoardPrefix;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 02/09/2019
 */
public class RedisBoardHelper {
    /**
     * 保存 board 信息到redis，key为roomNo
     * @param roomNo
     * @return
     */
    public static <T> void saveBoard(String roomNo, T board) {
        RedisUtil.set(BoardPrefix.ROOMNO_BOARD, roomNo, board);
    }

    /**
     * 通过 roomNo 获取 board 信息
     * @param roomNo
     * @return
     */
    public static <T> T getBoardByRoomNo(String roomNo, Class<T> clazz) {
        return RedisUtil.get(BoardPrefix.ROOMNO_BOARD, roomNo, clazz);
    }

}
