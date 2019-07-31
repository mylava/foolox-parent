package com.foolox.base.constant.game;

/**
 * comment: 游戏类型
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
public enum GameType {
    //牛牛
    BULLFIGHT,
    //跑得快
    RUNFAST,
    //德州扑克
    TEXAS_POKER,
    //
    MAHJONG,
    ;

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
