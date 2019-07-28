package com.foolox.base.poker.game;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * comment: 出牌类型
 *
 * @author: lipengfei
 * @date: 29/05/2019
 */
@Data
@NoArgsConstructor
public class CardType {
    private int maxcard;    //最大的牌
    private int cardtype;    //牌型
    private int typesize;    //不同牌数量
//    private boolean king;    //王炸
//    private boolean bomb;    //炸弹
    private int mincard;    //最小的牌
    private int cardnum;    //最大牌张数  ， JJJQ，= 3
    private byte maxcardvalue;

    public CardType(int maxcard , int cardtype){
        this.maxcard = maxcard ;
        this.cardtype = cardtype ;
//        this.king = king ;
//        this.bomb = bomb ;
    }
}
