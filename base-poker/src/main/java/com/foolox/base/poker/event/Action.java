package com.foolox.base.poker.event;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * comment: 牌局中玩家的动作
 *
 * @author: lipengfei
 * @date: 29/05/2019
 */
@Data
@NoArgsConstructor
public class Action {
    private byte card;
    private String action;
    private String type;            //动作类型， 杠 ： 明杠|暗杠|弯杠  ，  胡：胡法
    private long userid;
    private boolean gang;            //碰了以后，是否已再杠

    public Action(long userid, String action, String type, byte card) {
        this.userid = userid;
        this.action = action;
        this.type = type;
        this.card = card;
    }

    public Action(long userid, String action, byte card) {
        this.userid = userid;
        this.action = action;
        this.card = card;
    }
}
