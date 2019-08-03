package com.foolox.base.poker.facade;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.common.util.redis.RedisPlayerHelper;
import com.foolox.base.common.util.redis.RedisRoomHelper;
import com.foolox.base.constant.annotation.Facade;
import com.foolox.base.constant.annotation.MessageEvent;
import com.foolox.base.constant.game.PlayerStatus;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.io.handler.MessageHandler;
import com.foolox.base.io.sender.MessageSender;
import com.foolox.base.poker.domain.GameRoom;
import com.foolox.base.poker.domain.Playway;
import com.foolox.base.poker.engine.GameEngine;
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
    private GameEngine gameEngine;

    @Override
    public void execute(Long userId, JSONObject message) {
        log.info("join room request, request user is [{}]", userId);

        //房间号
        String roomNo = message.getString("roomNo");
        if (StringUtils.isEmpty(roomNo)) {
            log.info("join room failed, roomNo is null");
            MessageSender.sendToUser(userId, fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("roomNo")));
            return;
        }
        //房间号是否存在
        if (!RedisRoomHelper.existRoomNo(roomNo)) {
            log.info("join room failed, roomNo is incorrect");
            MessageSender.sendToUser(userId, fail(CodeMessage.ILLEGAL_ROOM_NO.fillArgs("roomNo")));
            return;
        }

        //玩家状态 如果已经在房间中，不能加入房间
        PlayerStatus playerStatus = RedisPlayerHelper.getPlayerStatus(userId);
        log.info("user [{}] status is [{}]", userId, playerStatus);
        if (!Objects.equals(playerStatus, PlayerStatus.IDLE)) {
            log.info("join room failed, user [{}] status is [{}]", userId, playerStatus);
            MessageSender.sendToUser(userId, fail(CodeMessage.ILLEGAL_STATUS_ERROR.fillArgs(playerStatus)));
            return;
        }

        GameRoom gameRoom = RedisRoomHelper.getRoomByNo(roomNo, GameRoom.class);


    }
}
