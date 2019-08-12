package com.foolox.base.model;

import com.foolox.base.constant.annotation.PlaywayMeta;
import com.foolox.base.constant.game.GameType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * comment: 所有玩法都需要实现此接口
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
@Data
@PlaywayMeta(GameType.BULLFIGHT)
@AllArgsConstructor
@NoArgsConstructor
public class Playway {
    //游戏类型
    private GameType gameType;
    //最大玩家数量（参与游戏）
    private Integer playerNum=10;
    //观众数量（不参与游戏）
    private Integer audienceNum;
    //是否允许中途加入
    private Boolean cutIn;
    //是否俱乐部房间
    private Boolean club;
    //加入限制
    private Integer joinLimit;
}
