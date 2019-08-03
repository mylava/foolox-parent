package com.foolox.game.niuniu.model;

import com.foolox.base.constant.annotation.PlaywayMeta;
import com.foolox.base.constant.game.GameType;
import com.foolox.base.constant.game.MarkType;
import com.foolox.base.constant.game.PlayerAction;
import com.foolox.base.constant.result.Result;
import com.foolox.base.poker.domain.GameRoom;
import com.foolox.base.poker.domain.Playway;
import com.foolox.base.poker.statemachine.GameEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
@Data
@PlaywayMeta(GameType.BULLFIGHT)
@AllArgsConstructor
@NoArgsConstructor
public class BullfightPlayway extends Playway {
    //游戏类型
    private GameType gameType;
    //最大玩家数量（参与游戏）
    private Integer playerNum=10;
    //观众数量（不参与游戏）
    private Integer audienceNum;
    //局数
    private Integer roundNum=10;
    //叫庄倍数
    private Integer hogDouble;
    //加入限制
    private Integer joinLimit;
    //叫分类型
    private MarkType markType;
    //是否允许中途加入
    private Boolean cutIn;
    //是否俱乐部房间
    private Boolean club;
    // 推注
    private Boolean redouble = true;
    // 同花顺
    private Boolean tonghuashun = false;
    // 顺子牛
    private Boolean shunziniu = false;
    // 通话牛
    private Boolean tonghuaniu = false;
    // 葫芦牛
    private Boolean huluniu = false;
    // 炸弹牛
    private Boolean zhadanniu = false;
    // 五花牛
    private Boolean wuxiaoniu = false;
    // 金牛
    private Boolean jinniu = false;
    // 银牛
    private Boolean yinniu = false;
    //翻倍规则
    private Integer rule;
    //房间结束自动创建
    private Boolean series;
    //最少开牌人数
    private Integer minStart=2;
    //最低抢庄
    private Integer hogFloor=1000;
    //最低下注
    private Integer betFloor=0;
    //开房费用
    private Integer cost;

    @Override
    public Result<GameEvent> createRoom(long userId, GameType gameType) {
        this.setGameType(gameType);
        //STAY 保存玩法到数据库

        GameRoom room = new GameRoom();
        room.setCreator(userId);
        room.setRoomNo(newRoomNo(6));
        room.setPlayway(this);
        room.setGameType(gameType);
        room.setClub(false);

        GameEvent gameEvent = new GameEvent();
        gameEvent.setPlayerId(userId);
        gameEvent.setRoomNo(room.getRoomNo());
        gameEvent.setPlayerAction(PlayerAction.CREATE);
        gameEvent.setGameRoom(room);

        return Result.success(gameEvent);
    }

    @Override
    public long getRoomCost() {
        return 0;
    }


}
