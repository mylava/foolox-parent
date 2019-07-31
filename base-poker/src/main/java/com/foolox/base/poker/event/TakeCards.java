package com.foolox.base.poker.event;

import com.foolox.base.poker.model.CardType;
import lombok.Data;

/**
 * comment: 当前出牌信息
 *
 * @author: lipengfei
 * @date: 29/05/2019
 */
@Data
public abstract class TakeCards {

    private String banker;     //庄家
    private boolean allow;        //符合出牌规则 ，
    private boolean donot;        //出 OR 不出
    private String userid;
    private byte[] cards;
//    private byte card ;			//麻将出牌

    private boolean over;        //已结束
    private boolean bomb;        //炸

    private long time;
    private int type;        //出牌类型 ： 1:单张 | 2:对子 | 3:三张 | 4:四张（炸） | 5:单张连 | 6:连对 | 7:飞机 : 8:4带2 | 9:王炸
    private CardType cardType;//出牌的牌型

    private String command;

    private boolean sameside;    //同一伙

    private int cardsnum;    //当前出牌的人 剩下多少张 牌

    private String nextplayer;        // 下一个出牌玩家
    private boolean automic;        //是否允许不出牌过
    private byte nextplayercard;    //下一个玩家翻到的新牌

    /**
     * 移除出牌
     *
     * @param cards
     * @param start
     * @param end
     * @return
     */
    public byte[] removeCards(byte[] cards, int start, int end) {
        byte[] retCards = new byte[cards.length - (end - start)];
        int inx = 0;
        for (int i = 0; i < cards.length; i++) {
            if (i < start || i >= end) {
                retCards[inx++] = cards[i];
            }
        }
        return retCards;
    }

    /**
     * 移除出牌
     *
     * @param cards
     * @param playcards
     * @return
     */
    public byte[] removeCards(byte[] cards, byte[] playcards) {
        byte[] retCards = new byte[cards.length - playcards.length];
        int cardsindex = 0;
        for (int i = 0; i < cards.length; i++) {
            boolean found = false;
            for (int inx = 0; inx < playcards.length; inx++) {
                if (cards[i] == playcards[inx]) {
                    found = true;
                    break;
                }
            }
            if (found == false) {
                retCards[cardsindex++] = cards[i];
            }
        }
        return retCards;
    }
}
