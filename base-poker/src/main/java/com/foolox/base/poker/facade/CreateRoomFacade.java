package com.foolox.base.poker.facade;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.common.strategy.ProcessorFactory;
import com.foolox.base.common.util.redis.RedisPlayerHelper;
import com.foolox.base.constant.annotation.Facade;
import com.foolox.base.constant.annotation.MessageEvent;
import com.foolox.base.constant.annotation.ProcessorType;
import com.foolox.base.constant.poker.PlayerStatus;
import com.foolox.base.constant.result.CodeMessage;
import com.foolox.base.constant.result.CommonMessage;
import com.foolox.base.io.handler.MessageHandler;
import com.foolox.base.io.sender.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * comment: 创建房间
 * 实际上只在创建Redis中创建房间，真正创建房间是在第一个用户进入房间的时候
 *
 * @author: lipengfei
 * @date: 29/07/2019
 */
@Slf4j
@Facade(value = MessageEvent.CREATE_ROOM)
public class CreateRoomFacade extends MessageHandler {
    @Autowired
    private ProcessorFactory factory;

    @Override
    public void execute(String userId, JSONObject message) {
        log.info("create room request, request user is [{}]", userId);

        PlayerStatus playerStatus = RedisPlayerHelper.getPlayerStatus(userId);
        log.info("user [{}] status is [{}]", userId, playerStatus);
        if (!Objects.equals(playerStatus, PlayerStatus.IDLE)) {
            log.info("create room failed, user [{}] status is [{}]", userId, playerStatus);
            MessageSender.sendToUser(userId, fail(CodeMessage.ILLEGAL_STATUS_ERROR.fillArgs(playerStatus)));
            return;
        }

        String roomType = message.getString("roomType");
        if (StringUtils.isEmpty(roomType)) {
            log.info("create room failed, roomType is null");
            MessageSender.sendToUser(userId, fail(CodeMessage.PARAMS_EMPTY_ERROR.fillArgs("roomType")));
            return;
        }

        factory.createStrategy(getCommand(), ProcessorType.nameOf(roomType)).process(userId, message);
    }
}
