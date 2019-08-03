package com.foolox.base.poker.engine;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.common.strategy.ProcessorFactory;
import com.foolox.base.common.util.SpringContextUtil;
import com.foolox.base.constant.annotation.ProcessorType;
import com.foolox.base.constant.game.PlayerAction;
import com.foolox.base.constant.game.RoomStatus;
import com.foolox.base.constant.statemachine.MessageKey;
import com.foolox.base.poker.domain.GameRoom;
import com.foolox.base.poker.statemachine.GameEvent;
import com.foolox.base.poker.util.RoomContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ProcessorFactory processorFactory;

    /**
     * 创建房间
     *
     * @param command 响应key(与请求key一致)
     * @param userId  玩家ID
     * @param message 创建房间的参数
     */
    public void createRoom(String command, ProcessorType processorType, Long userId, JSONObject message) {
        processorFactory.createStrategy(command, processorType).process(userId, message);
    }

    /**
     * 加入房间
     *
     * @param playerId
     * @param roomNo
     */
    public void joinRoom(Long playerId, String roomNo) {
        GameRoom room = RoomContext.getRoom(roomNo);
        room.lockRoom();

        if (room.getPlayway().canJoin(room)) {

        }



    }


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
