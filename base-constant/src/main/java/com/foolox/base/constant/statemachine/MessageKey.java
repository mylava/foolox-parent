package com.foolox.base.constant.statemachine;

/**
 * comment: 状态机传递消息使用的常量
 *
 * @author: lipengfei
 * @date: 03/08/2019
 */
public enum MessageKey {
    //玩家ID
    PLAYER_ID,
    ROOM_NO,
    GAME_TYPE,
    GAME_ROOM,
    ;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
