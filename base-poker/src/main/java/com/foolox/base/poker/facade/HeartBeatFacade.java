package com.foolox.base.poker.facade;

import com.alibaba.fastjson.JSONObject;
import com.foolox.base.constant.annotation.Facade;
import com.foolox.base.constant.annotation.MessageEvent;
import com.foolox.base.io.handler.MessageHandler;
import com.foolox.base.io.sender.MessageSender;
import lombok.extern.slf4j.Slf4j;

/**
 * comment: 心跳
 *
 * @author: lipengfei
 * @date: 30/08/2019
 */
@Slf4j
@Facade(value = MessageEvent.HEART_BEAT)
public class HeartBeatFacade extends MessageHandler {
    @Override
    public void execute(Long playerId, JSONObject message) {
        log.info("heart beat {},{}", playerId, message);

        MessageSender.sendToUser(playerId, success("heartbeat"));
    }
}
