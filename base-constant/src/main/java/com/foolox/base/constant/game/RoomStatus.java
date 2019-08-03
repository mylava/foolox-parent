package com.foolox.base.constant.game;

/**
 * comment: 房间状态
 *
 * @author: lipengfei
 * @date: 01/08/2019
 */
public enum RoomStatus {
    /**
     * 游戏的基本状态，开局->等待玩家（AI）->凑齐一桌子->打牌->结束
     */
    NONE,		//无状态
    CREATE,    //创建完成
    JOININ,    //进入房间
    WAITTING,  //等待玩家
    READY,     //凑齐一桌子
    BEGIN,     //开始游戏
    PLAY,      //打牌
    LASTHANDS, //翻底牌
    DISSOLVE,  //解散中
    RESTART,   //下一局
    END;       //结束
}
