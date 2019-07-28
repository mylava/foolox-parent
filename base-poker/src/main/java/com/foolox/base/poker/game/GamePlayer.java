package com.foolox.base.poker.game;

import com.foolox.base.common.result.OutMessage;
import com.foolox.base.poker.event.Action;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * comment: 单个玩家牌局内的信息
 *
 * @author: lipengfei
 * @date: 29/05/2019
 */
@Data
@NoArgsConstructor
public class GamePlayer implements OutMessage {
    private String playuserId;    //userid对应
    private byte[] cards;    //玩家手牌，顺序存储 ， 快速排序（4个Bit描述一张牌，玩家手牌 麻将 13+1/2 = 7 byte~=long）
    private byte[] history = new byte[]{};//出牌历史 ， 特权可看
    private byte info;        //复合信息存储，用于存储玩家位置（2^4,占用4个Bit，最大支持16个玩家）（是否在线1个Bit），是否庄家/地主（1个Bit），是否当前出牌玩家（1个Bit）（是否机器人1个Bit）
    //    private boolean randomcard;    //起到地主牌的人
//    private boolean docatch;    //抢过庄（地主）
//    private boolean recatch;    //补抢
    private int deskcards;    //剩下多少张牌

    //    private boolean hu;    //已经胡过牌了
//    private boolean end;    //血战的时候，标记 结束
    private String command;

//    private boolean selected;    //已经选择 花色
//    private int color;        //定缺 花色   0  : wan , 1:tong , 2 :tiao

    //    private boolean accept;    //抢地主 : 过地主
    private boolean banker;    //庄家
    private byte[] played;    //杠碰吃胡
    private List<Action> actions = new ArrayList<Action>();

//    private String event;

    public GamePlayer(String playuserId) {
        this.playuserId = playuserId;
    }

    @Override
    protected GamePlayer clone() {
        try {
            return (GamePlayer) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
