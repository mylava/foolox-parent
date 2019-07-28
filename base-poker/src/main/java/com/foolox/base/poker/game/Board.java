package com.foolox.base.poker.game;

import com.foolox.base.common.result.OutMessage;
import com.foolox.base.poker.event.TakeCards;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * comment: 牌桌抽象对象
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
@Data
public abstract class Board implements OutMessage {
    private String id;

    private byte[] cards;    //4个Bit描述一张牌，麻将：136+2/2 = 69 byte ; 扑克 54/2 = 27 byte
    private GamePlayer[] gamePlayers;//3~10人(4 byte)

    private List<Byte> deskcards;

    private TakeCards last;

    private boolean finished;

    private List<Byte> history = new ArrayList<Byte>();

    private String winner;        //赢的玩家

    private NextPlayer nextplayer;

    private String room;        //房间ID（4 byte）

    private byte[] lasthands;    //底牌

    private byte[] powerful;    //癞子 ， 麻将里的 单张癞子 ， 地主里的 天地癞子

    private int position;        //地主牌

    private boolean docatch;    //叫地主 OR 抢地主
    private int ratio = 1;            //倍数
    private boolean added;            //已翻倍

    private String banker;        //庄家|地主 Id
    private String currplayer;    //当前出牌人
    private byte currcard;        //当前出牌
    private String command;
    private String event;

    /**
     * @return
     */
    public abstract byte[] pollLastHands();

    /**
     * 计算倍率， 每种游戏规则不同，斗地主 翻到底牌 大小王 翻倍
     *
     * @return
     */
    public abstract int calcRatio();


    public abstract TakeCards takeCards(GamePlayer gamePlayer, String playerType, TakeCards current);

    /**
     * 找到玩家的 位置
     *
     * @param userid
     * @return
     */
    public abstract int index(String userid);


    /**
     * 计算结算信息
     *
     * @return
     */
    public abstract Summary summary(Board board, GameRoom gameRoom);

    /**
     * 是否赢了
     *
     * @return
     */
    public abstract boolean isWin();


    /**
     * 找到下一个玩家
     *
     * @param index
     * @return
     */
    protected abstract GamePlayer next(int index);


    public abstract GamePlayer nextPlayer(int index);

    /**
     * 玩家随机出牌，能管住当前出牌的 最小牌
     *
     * @param gamePlayer
     * @return
     */
    public abstract TakeCards takecard(GamePlayer gamePlayer, boolean allow, byte[] playCards);

    /**
     * 当前玩家随机出牌
     *
     * @param gamePlayer
     * @return
     */
    public abstract TakeCards takecard(GamePlayer gamePlayer);

    /**
     * 出牌请求
     *
     * @param auto
     * @param playCards
     * @return
     */
    public abstract TakeCards takeCardsRequest(GameRoom gameRoom, Board board, GamePlayer gamePlayer, boolean auto, byte[] playCards);

    /**
     * 发牌动作
     *
     * @param gameRoom
     * @param board
     */
    public abstract void dealRequest(GameRoom gameRoom, Board board, boolean reverse, String nextplayer);

    /**
     * 下一个出牌玩家
     *
     * @param board
     * @param gameRoom
     * @param gamePlayer
     */
    public abstract void playcards(Board board, GameRoom gameRoom, GamePlayer gamePlayer);

    /**
     * 玩家出牌
     *
     * @param gamePlayer
     * @return
     */
    public abstract TakeCards takecard(GamePlayer gamePlayer, TakeCards last);


    private static final long serialVersionUID = 1L;

    /**
     * 找到玩家数据
     *
     * @param userid
     * @return
     */
    public GamePlayer getGamePlayer(String userid) {
        GamePlayer temp = null;
        if (this.gamePlayers != null) {
            for (GamePlayer user : gamePlayers) {
                if (user.getPlayuserId() != null && user.getPlayuserId().equals(userid)) {
                    temp = user;
                    break;
                }
            }
        }
        return temp;
    }

    /**
     * 找到玩家数据
     *
     * @param userid
     * @return
     */
//    public ClientSession getClientSession(List<ClientSession> clientSessionList, String userid) {
//        ClientSession temp = null;
//        for (ClientSession clientSession : clientSessionList) {
//            if (clientSession.getId().equals(userid)) {
//                temp = clientSession;
//                break;
//            }
//        }
//        return temp;
//    }
}
