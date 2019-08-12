package com.foolox.base.common.strategy;

import com.foolox.base.constant.annotation.Strategy;
import com.foolox.base.constant.game.GameType;
import com.foolox.base.db.domain.GameRoom;
import com.foolox.base.model.Playway;
import lombok.extern.slf4j.Slf4j;

/**
 * comment: 默认策略，当根据key找不到策略时使用
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
@Slf4j
@Strategy(type = GameType.NULL)
public class NullGameService extends GameService {
    @Override
    public long getRoomCost() {
        return 0;
    }

    @Override
    public void createRoom(long playerId, GameType gameType, Playway playway) {

    }

    @Override
    public boolean canJoin(long playerId, GameRoom gameRoom) {
        return false;
    }

    @Override
    public void joinRoom(long playerId, GameRoom gameRoom) {

    }
}
