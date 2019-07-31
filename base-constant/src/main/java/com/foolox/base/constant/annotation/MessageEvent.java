package com.foolox.base.constant.annotation;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
public enum MessageEvent {
    //玩家游戏状态
    GAME_STATUS,
    JOIN_ROOM,
    CREATE_ROOM,
    ;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
