package com.foolox.base.poker.facade;

import com.foolox.base.constant.annotation.MessageMapping;
import com.foolox.base.poker.message.in.JoinRoomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 28/07/2019
 */
@Slf4j
@Controller
public class JoinRoomFacade {

    @MessageMapping
    public void joinRoom(String userId, JoinRoomMessage message) {
        log.info("{},{}", userId, message);
//        MessageSender.sendToUser(userId,"hello",message);
    }
}
