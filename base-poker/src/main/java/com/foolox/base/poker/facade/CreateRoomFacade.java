package com.foolox.base.poker.facade;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.common.strategy.ProcessorFactory;
import com.foolox.base.common.util.redis.RedisPlayerHelper;
import com.foolox.base.constant.annotation.Facade;
import com.foolox.base.constant.annotation.MessageEvent;
import com.foolox.base.constant.annotation.ProcessorType;
import com.foolox.base.constant.game.PlayerStatus;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.io.handler.MessageHandler;
import com.foolox.base.io.sender.MessageSender;
import com.foolox.base.poker.engine.GameEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * comment: 创建房间
 *
 * @author: lipengfei
 * @date: 29/07/2019
 */
@Slf4j
@Facade(value = MessageEvent.CREATE_ROOM)
public class CreateRoomFacade extends MessageHandler {
    @Autowired
    private GameEngine gameEngine;

    @Override
    public void execute(Long userId, JSONObject message) {
        log.info("create room request, request user is [{}]", userId);

        //玩家状态 如果已经在房间中，不能创建房间
        PlayerStatus playerStatus = RedisPlayerHelper.getPlayerStatus(userId);
        log.info("user [{}] status is [{}]", userId, playerStatus);
        if (!Objects.equals(playerStatus, PlayerStatus.IDLE)) {
            log.info("create room failed, user [{}] status is [{}]", userId, playerStatus);
            MessageSender.sendToUser(userId, fail(CodeMessage.ILLEGAL_STATUS_ERROR.fillArgs(playerStatus)));
            return;
        }

        //房间类型
        String roomType = message.getString("roomType");
        if (StringUtils.isEmpty(roomType)) {
            log.info("create room failed, roomType is null");
            MessageSender.sendToUser(userId, fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("roomType")));
            return;
        }

        //游戏类型
        String gameType = message.getString("gameType");
        if (StringUtils.isEmpty(gameType)) {
            log.info("common room create request, gameType is empty");
            MessageSender.sendToUser(userId, fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("gameType")));
            return;
        }
        log.info("common room create request, gameType is [{}]", gameType);

        gameEngine.createRoom(getCommand(), ProcessorType.nameOf(roomType), userId, message);
    }
}
