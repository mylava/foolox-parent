package com.foolox.base.poker.engine;

import com.foolox.base.common.statemachine.GameEvent;
import com.foolox.base.common.util.SpringContextUtil;
import com.foolox.base.constant.game.PlayerAction;
import com.foolox.base.constant.game.RoomStatus;
import com.foolox.base.constant.statemachine.MessageKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 20/05/2019
 */
@Slf4j
@Component
public class GameEngine {
    /**
     * 通知状态机，房间状态发生改变
     *
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void stateChange(GameEvent gameEvent) {
        StateMachine<RoomStatus, PlayerAction> stateMachine = (StateMachine<RoomStatus, PlayerAction>) SpringContextUtil
                .getApplicationContext().getBean(gameEvent.getGameType().toString());
        stateMachine.sendEvent(new Message<PlayerAction>() {
            @Override
            public PlayerAction getPayload() {
                return gameEvent.getPlayerAction();
            }

            @Override
            public MessageHeaders getHeaders() {
                HashMap<String, Object> headers = new HashMap<>();
                headers.put(MessageKey.PLAYER_ID.toString(), gameEvent.getPlayerId());
                headers.put(MessageKey.ROOM_NO.toString(), gameEvent.getRoomNo());
                headers.put(MessageKey.GAME_TYPE.toString(), gameEvent.getGameType());
                headers.put(MessageKey.GAME_ROOM.toString(), gameEvent.getGameRoom());
                return new MessageHeaders(headers);
            }
        });
    }


}
