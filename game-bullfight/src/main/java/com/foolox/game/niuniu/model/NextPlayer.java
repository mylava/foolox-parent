package com.foolox.game.niuniu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 29/05/2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NextPlayer {
    //下一个玩家ID
    private String nextplayer;
    //是否出牌
    private boolean takecard;
}
