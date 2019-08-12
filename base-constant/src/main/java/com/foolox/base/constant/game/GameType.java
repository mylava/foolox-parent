package com.foolox.base.constant.game;

/**
 * comment: 游戏类型
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
public enum GameType {
    //空
    NULL(0),
    //牛牛
    BULLFIGHT(1),
    //跑得快
    RUNFAST(2),
    //德州扑克
    TEXAS_POKER(3),
    //
    MAHJONG(4),
    ;

    int value;

    GameType(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static GameType nameOf(String name) {
        for (GameType gameType : GameType.values()) {
            if (null != name && name.toLowerCase().equals(gameType.toString())) {
                return gameType;
            }
        }
        return null;
    }

}
