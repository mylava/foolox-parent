package com.foolox.base.constant.game;

/**
 * comment: 玩家事件（对房间的操作）
 *
 * @author: lipengfei
 * @date: 02/08/2019
 */
public enum PlayerAction {
    //创建房间
    CREATE,
    JOIN,		//成员加入
    AUTO,		//自动 , 抢地主
    ENOUGH,		//凑够一桌子
    RAISEHANDS,	//流程处理完毕，开始出牌
    PLAYCARDS,	//出牌
    ALLCARDS,	//1、单个玩家打完牌（地主，推到胡）；2、打完桌面的所有牌（血战，血流，德州）
    DEAL,		//抓牌动作
    SELECT ;	//麻将的特别事件 ， 定缺
}
