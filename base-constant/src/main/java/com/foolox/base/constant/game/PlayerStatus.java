package com.foolox.base.constant.game;

/**
 * comment: 玩家的游戏状态
 *
 * @author: lipengfei
 * @date: 19/05/2019
 */
public enum PlayerStatus {
    //玩家状态
    IDLE(0),           //空闲（登录成功，尚未开始游戏）
    WATCH(1),          //观战（已进入房间，未坐下）
    SITDOWN(2),        //坐下（已坐下，未准备）
    READY(3),          //准备
    PLAYING(4),        //游戏中(包含结算中？)
    MANAGED(5),        //托管
    SETTLEMENT(6),     //结算中
    TIMEOUT(7),        //会话超时
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
