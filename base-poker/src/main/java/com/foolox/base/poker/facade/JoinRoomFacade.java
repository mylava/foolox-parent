package com.foolox.base.poker.facade;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.common.strategy.GameService;
import com.foolox.base.common.strategy.ServiceFactory;
import com.foolox.base.common.util.redis.RedisSystemHelper;
import com.foolox.base.common.util.redis.RedisPlayerHelper;
import com.foolox.base.constant.annotation.Facade;
import com.foolox.base.constant.annotation.MessageEvent;
import com.foolox.base.constant.game.PlayerStatus;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.db.domain.GameRoom;
import com.foolox.base.io.handler.MessageHandler;
import com.foolox.base.io.sender.MessageSender;
import com.foolox.base.poker.util.RoomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
@Slf4j
@Facade(MessageEvent.JOIN_ROOM)
public class JoinRoomFacade extends MessageHandler {

    @Autowired
    private ServiceFactory serviceFactory;

    @Override
    public void execute(Long playerId, JSONObject message) {
        log.info("player [{}] join room request", playerId);

        //房间号
        String roomNo = message.getString("roomNo");
        if (StringUtils.isEmpty(roomNo)) {
            log.info("player [{}] join room failed, roomNo is null", playerId);
            MessageSender.sendToUser(playerId, fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("roomNo")));
            return;
        }
        //房间号是否存在
        if (!RedisSystemHelper.existRoomNo(roomNo)) {
            log.info("player [{}] join room failed, roomNo [{}] is incorrect", playerId, roomNo);
            MessageSender.sendToUser(playerId, fail(CodeMessage.ILLEGAL_ROOMNO));
            return;
        }
        //房间是否在当前服务器上
        if (!RoomUtil.existRoomNo(roomNo)) {
            log.error("player [{}] join room failed, machine is incorrect", playerId);
            MessageSender.sendToUser(playerId, fail(CodeMessage.MACHINEID_INCORRECT));
            return;
        }

        //玩家状态 如果已经在房间中，不能加入房间
        PlayerStatus playerStatus = RedisPlayerHelper.getPlayerStatus(playerId);
        log.info("player [{}] status is [{}]", playerId, playerStatus);
        if (!Objects.equals(playerStatus, PlayerStatus.LOGIN)) {
            log.info("player [{}] join room failed, player status is [{}]", playerId, playerStatus);
            MessageSender.sendToUser(playerId, fail(CodeMessage.ILLEGAL_STATUS_ERROR.fillArgs(playerStatus)));
            return;
        }

        GameRoom room = RoomUtil.getRoom(roomNo);
        try {
            room.lockRoom();
            GameService gameService = serviceFactory.createStrategy(getCommand(), room.getGameType());
            if (!gameService.canJoin(playerId, room)) {
                return;
            }

            log.info("before check pass, player [{}] start to [join room]", playerId);
            gameService.joinRoom(playerId, room);
        } finally {
            room.unlockRoom();
        }

    }
}
