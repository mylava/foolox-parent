package com.foolox.base.constant.game;

/**
 * comment: 玩家的游戏状态
 *
 * @author: lipengfei
 * @date: 19/05/2019
 */
public enum PlayerStatus {
    //玩家状态
    LOGIN(0),           //已登录（http登录）
    ONLINE(1),          //socket已连接
    WATCH(2),          //观战（已进入房间，未坐下）
    SITDOWN(3),        //坐下（已坐下，未准备）
    READY(4),          //准备
    PLAYING(5),        //游戏中(包含结算中？)
    MANAGED(6),        //托管
    SETTLEMENT(7),     //结算中
    TIMEOUT(8),        //会话超时
    ;
    int value;

    PlayerStatus(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static PlayerStatus nameOf(String name) {
        for (PlayerStatus playerStatus : PlayerStatus.values()) {
            if (null != name && name.toLowerCase().equals(playerStatus.toString())) {
                return playerStatus;
            }
        }
        return null;
    }
}
