package com.foolox.base.common.statemachine;

import com.foolox.base.constant.game.GameType;
import com.foolox.base.constant.game.PlayerAction;
import com.foolox.base.db.domain.GameRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * comment: 游戏事件
 *
 * @author: lipengfei
 * @date: 02/08/2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameEvent{
    //房间号
    private String roomNo;
    //用户ID
    private long playerId;
    //游戏类型
    private GameType gameType;
    //用户动作
    private PlayerAction playerAction;
    //房间对象
    private GameRoom gameRoom;
    //当前玩家 顺序号
    private int index;
    //游戏人数
    private int players;
    //每个玩家发牌数量
    private int cardsnum;
}
