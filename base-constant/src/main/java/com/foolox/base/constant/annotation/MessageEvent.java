package com.foolox.base.constant.annotation;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
public enum MessageEvent {
    //所有与客户端交互的命令
    HEART_BEAT, //心跳
    GAME_STATUS,//玩家游戏状态
    JOIN_ROOM,//加入房间
    CREATE_ROOM,//创建房间
    ;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
