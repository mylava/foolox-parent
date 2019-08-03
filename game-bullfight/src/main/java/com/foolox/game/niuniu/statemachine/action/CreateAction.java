package com.foolox.game.niuniu.statemachine.action;

import com.foolox.base.common.util.AsyncDbUtil;
import com.foolox.base.common.util.SpringContextUtil;
import com.foolox.base.constant.game.PlayerAction;
import com.foolox.base.constant.game.RoomStatus;
import com.foolox.base.constant.statemachine.MessageKey;
import com.foolox.base.poker.dao.GameRoomRepository;
import com.foolox.base.poker.domain.GameRoom;
import com.foolox.base.poker.engine.GameEngine;
import com.foolox.base.poker.util.RoomContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * comment: 创建房间后的动作
 *
 * @author: lipengfei
 * @date: 03/08/2019
 */
@Slf4j
public class CreateAction implements Action<RoomStatus, PlayerAction>{
    @Override
    public void execute(StateContext<RoomStatus, PlayerAction> stateContext) {
        MessageHeaders messageHeaders = stateContext.getMessageHeaders();
        Long playerId = (Long) messageHeaders.get(MessageKey.PLAYER_ID.toString());
        GameRoom room = (GameRoom) messageHeaders.get(MessageKey.GAME_ROOM.toString());
        room.setRoomStatus(RoomStatus.CREATE);
        room.setCurrentnum(0);

        //创建完成后保存到内存中
        if (null!=RoomContext.saveRoom(room.getRoomNo(), room)){
            log.error("已存在的房间号");
        }

        //保存到数据库
        AsyncDbUtil.published(room, SpringContextUtil.getBean(GameRoomRepository.class));

        GameEngine gameEngine = SpringContextUtil.getBean(GameEngine.class);
        //新创建的房间，不用任何判断，直接加入
        gameEngine.joinRoom(playerId, room.getRoomNo());
    }
}
