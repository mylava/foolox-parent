package com.foolox.base.constant.poker;

/**
 * comment: 玩家的游戏状态
 *
 * @author: lipengfei
 * @date: 19/05/2019
 */
public enum PlayerStatus {
    //玩家状态
    READY,          //就绪 （尚未开始游戏）
    NOTREADY,		//未就绪（不能游戏）
    MANAGED,        //托管
    PLAYING,        //游戏中(包含结算中？)
    SETTLEMENT,     //结算中
    TIMEOUT;		//会话超时
    @Override
    public String toString(){
        return super.toString().toLowerCase() ;
    }
}
